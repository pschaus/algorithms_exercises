import os
import random
import string
import shutil
import re
import zipfile

script_dir = os.path.abspath(os.path.dirname(__file__))

source_dir = os.path.join(script_dir, 'src', 'main', 'java')
test_dir = os.path.join(script_dir, 'src', 'test', 'java')

inginious_dir = os.path.join(script_dir, 'inginious')
templates_dir = os.path.join(script_dir, 'templates')

course_name = 'LINFO1121'

def _safe_mkdir(directory, delete=False):
    if delete and os.path.exists(directory):
        shutil.rmtree(directory)
    os.makedirs(directory, exist_ok=True)


def get_dir_from_exercices(exs):
    return course_name + '_'.join(exs)

def get_packages():
    return [f for f in os.listdir(source_dir) if os.path.isdir(os.path.join(source_dir, f))]

def get_exercises(package):
    """Get the list of exercises from the source directory"""
    directory = os.path.join(source_dir, package)
    # Note that for some exercises, there are "generators" which are used to generate data for testing.
    # These files are not exercises per se and should be omited from the exercise list.
    # They are name <exercise_name>Generator.java
    return [os.path.splitext(os.path.basename(f))[0] for f in os.listdir(directory) if os.path.isfile(os.path.join(directory, f)) and not str(f).endswith('Generator.java')]

packages = get_packages()
exercises = {p: get_exercises(p) for p in packages}

def strip_file(filename, outfile=None):
    lines = open(filename, 'r').readlines()
    out = filename if outfile is None else outfile
    with open(out, 'w') as fout:
        pruning = False
        for line in lines:
            if "BEGIN STRIP" in line:
                pruning = True
                continue
            elif "END STRIP" in line:
                pruning = False
                continue
            if not pruning:
                fout.write(re.sub('\/\/.*STUDENT(S?)', "", line))


def strip_directory(path):
    for file in os.listdir(path):
        strip_file(os.path.join(path, file))

def createTestFile(package, exercise, exercise_dir):
    inname = exercise + 'Test.java'
    outname = exercise + 'TestInginious.java'
    _safe_mkdir(os.path.join(exercise_dir, package))
    outfile = open(os.path.join(exercise_dir, package, outname), 'w')
    with open(os.path.join(test_dir, package, inname)) as f:
        buffer = list()
        in_test = False
        count_open_acc = 0
        for line in f:
            if line.startswith(f'public class {exercise}Test'):
                outfile.write(line.replace(f'{exercise}Test', f'{exercise}TestInginious'))
            else:
                outfile.write(line)
    outfile.close()


def create_exercise_task(package, exercise):
    exercise_dir_name = f'{package}_{exercise}'
    exercise_dir = os.path.join(inginious_dir, exercise_dir_name)
    _safe_mkdir(exercise_dir)
    _safe_mkdir(os.path.join(exercise_dir, 'public'))

    # copy the data for the test
    if os.path.exists(os.path.join(script_dir, 'data',exercise)):
        shutil.copytree(os.path.join(script_dir, 'data',exercise),
                        os.path.join(exercise_dir, 'data',exercise))

    # copy libs
    shutil.copytree(os.path.join(templates_dir, 'libs'),
                    os.path.join(exercise_dir, 'libs'))

    # Copy the test file to the src directory
    createTestFile(package, exercise, exercise_dir)

    # Test runner
    runner_file = 'RunTests.java'
    with open(os.path.join(templates_dir, runner_file), 'r') as f:
        test_runner_content = ''.join(f.readlines())

    with open(os.path.join(exercise_dir, runner_file), 'w') as f:
        f.write(test_runner_content.format(package, exercise + 'TestInginious'))

    # Run file
    run_file = 'run'
    with open(os.path.join(templates_dir, run_file), 'r') as f:
        run_content = ''.join(f.readlines())

    with open(os.path.join(exercise_dir, run_file), 'w') as f:
        f.write(run_content.format(package, exercise))


    # write the template file
    with open(os.path.join(exercise_dir, package, exercise + '.java'), 'w') as f:
        f.write('@@code@@')

    with open(os.path.join(templates_dir, 'task.yaml.tpl'), 'r') as f:
            task_config_tpl = ''.join(f.readlines())

    with open(os.path.join(exercise_dir, 'task.yaml'), 'w') as f:
        f.write(task_config_tpl.format(
            course_name,
            exercise_dir_name,
            exercise + '.zip',
            exercise))
    # copy the archive in the public directory of the exercise so it can be downloaded
    # by the students
    shutil.copy(os.path.join('zips', package, exercise + '.zip'),
            os.path.join(exercise_dir, 'public', f'{exercise}.zip'))



def generate_inginious_tasks():
    """Generate the INGInious tasks
    """
    _safe_mkdir(inginious_dir, delete=True)
    for package in packages:
        for exercise in exercises[package]:
            create_exercise_task(package, exercise)
    generate_course_yaml()

def generate_course_yaml():
    with open(os.path.join(templates_dir, 'course.yaml.tpl'), 'r') as f:
              tpl = ''.join(f.readlines())
    toc = ""
    for i, package in enumerate(packages):
        toc += f"- id: {package}\n  title: {package}\n  rank: {i}\n  tasks_list:\n"
        for j, exercise in enumerate(exercises[package]):
            toc += f"    {package}_{exercise}: {j}\n"

    with open(os.path.join(inginious_dir, 'course.yaml'), 'w') as f:
        f.write(tpl.format(toc))
    

def create_stripped_project():
    # This function copy the whole java project in the `stripped_project` directory
    # which is created if necessary. The copied file are stripped so this is a clean
    # java project which can be given to the students
    directory = os.path.join(script_dir, 'stripped_project')
    _safe_mkdir(directory, delete=True)
    stripped_src = os.path.join(directory, 'src', 'main', 'java')
    stripped_test = os.path.join(directory, 'src', 'test', 'java')
    _safe_mkdir(stripped_src)
    _safe_mkdir(stripped_test)
    for package in packages: 
        _safe_mkdir(os.path.join(stripped_src, package))
        _safe_mkdir(os.path.join(stripped_test, package))
        for exercise in exercises[package]:
            in_src = os.path.join(source_dir, package, exercise + '.java')
            in_test = os.path.join(test_dir, package, exercise + 'Test.java')
            strip_file(in_src, outfile=os.path.join(stripped_src, package, exercise + '.java'))
            strip_file(in_test, outfile=os.path.join(stripped_test, package, exercise + 'Test.java'))
    shutil.copytree(os.path.join(script_dir, 'templates', 'libs'),
            os.path.join(directory, 'libs'))
    shutil.copyfile(os.path.join(script_dir, 'pom.xml'),
            os.path.join(directory, 'pom.xml'))

def make_archives():
    # This functions creates the archives containing the maven projects for the exercises
    # The following archives are created:
    #   - zips/package/ex1.zip, ex2.zip ... (one exercise per archive, for each package)
    directory = os.path.join(script_dir, 'zips')
    _safe_mkdir(directory, delete=True)

    base_dir = os.path.join(script_dir, "stripped_project")

    def get_archive_path(p):
        return os.path.relpath(p, script_dir)

    for package in packages:
        _safe_mkdir(os.path.join(directory, package), delete=True)
        for exercise in exercises[package]:
            zip_file = zipfile.ZipFile(os.path.join(directory, package, exercise + '.zip'), "w")
            zip_file.write(get_archive_path(os.path.join(base_dir, 'pom.xml')))
            zip_file.write(get_archive_path(os.path.join(base_dir, 'src', 'main', 'java', package, f'{exercise}.java')))
            zip_file.write(get_archive_path(os.path.join(base_dir, 'src', 'test', 'java', package, f'{exercise}Test.java')))
            for lib in os.listdir(os.path.join(base_dir, 'libs')):
                zip_file.write(get_archive_path(os.path.join(base_dir, 'libs', lib)))
            zip_file.close()

if __name__ == '__main__':
    create_stripped_project()
    make_archives()
    generate_inginious_tasks()
