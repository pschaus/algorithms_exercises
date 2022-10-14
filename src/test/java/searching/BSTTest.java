package searching;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;


@RunWith(Enclosed.class)
public class BSTTest {

    public static class TestNotParameterized {

        @Test
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example", onFail=true)
        public void testSimple() {
            int[] preOrderInput = {10,5,7,14,12};
            BST student = new BST(preOrderInput);
            BST teacher = new BST();
            teacher.root = new BST.Node(new BST.Node(null, new BST.Node(null, null, 7), 5), new BST.Node(new BST.Node(null, null, 12), null, 14), 10);
            assertEquals(teacher, student);

        }

        BST.Node [] bst5 = new BST.Node[] {
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,254),null,313),new BST.Node(null,null,847),588),null,985),
            new BST.Node(new BST.Node(null,new BST.Node(null,new BST.Node(null,null,748),606),434),new BST.Node(null,null,978),904),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,263),null,317),new BST.Node(null,null,562),473),null,569),
            new BST.Node(new BST.Node(null,null,189),new BST.Node(new BST.Node(null,null,262),new BST.Node(null,null,596),592),234),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,99),null,310),null,332),new BST.Node(null,null,674),376),
            new BST.Node(new BST.Node(new BST.Node(null,null,153),new BST.Node(new BST.Node(null,null,302),null,437),298),null,959),
            new BST.Node(new BST.Node(null,null,6),new BST.Node(new BST.Node(new BST.Node(null,null,363),null,800),null,854),205),
            new BST.Node(new BST.Node(new BST.Node(null,null,75),new BST.Node(null,new BST.Node(null,null,834),820),689),null,955),
            new BST.Node(null,new BST.Node(new BST.Node(null,null,477),new BST.Node(null,null,737),660),415),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,357),null,500),220),new BST.Node(null,null,888),592),
            new BST.Node(new BST.Node(new BST.Node(null,null,55),null,333),new BST.Node(new BST.Node(null,null,536),null,676),452),
            new BST.Node(new BST.Node(null,null,229),new BST.Node(null,new BST.Node(null,new BST.Node(null,null,884),504),358),345),
            new BST.Node(new BST.Node(null,new BST.Node(null,new BST.Node(null,null,598),486),151),new BST.Node(null,null,683),600),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,528),null,865),514),null,908),null,996),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,522),58),null,615),null,716),null,859),
            new BST.Node(new BST.Node(null,null,1),new BST.Node(null,new BST.Node(null,new BST.Node(null,null,797),731),192),87),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,485),241),null,562),new BST.Node(null,null,708),688),
            new BST.Node(null,new BST.Node(null,new BST.Node(null,new BST.Node(null,new BST.Node(null,null,810),759),695),379),69),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,53),null,374),null,526),new BST.Node(null,null,620),617),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,436),new BST.Node(null,null,650),601),101),null,825),
            new BST.Node(new BST.Node(new BST.Node(null,null,94),new BST.Node(new BST.Node(null,null,494),null,575),232),null,674),
            new BST.Node(new BST.Node(null,new BST.Node(null,new BST.Node(null,null,547),543),508),new BST.Node(null,null,903),624),
            new BST.Node(new BST.Node(new BST.Node(null,null,151),new BST.Node(null,new BST.Node(null,null,857),833),180),null,967),
            new BST.Node(null,new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(null,null,569),450),null,745),377),366),
            new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(null,null,116),76),new BST.Node(null,null,490),203),8),
            new BST.Node(new BST.Node(null,null,19),new BST.Node(new BST.Node(null,null,234),new BST.Node(null,null,699),621),96),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,295),271),new BST.Node(null,new BST.Node(null,null,705),550),528),
            new BST.Node(new BST.Node(new BST.Node(null,null,16),new BST.Node(null,null,436),291),new BST.Node(null,null,864),856),
            new BST.Node(new BST.Node(null,null,28),new BST.Node(new BST.Node(null,null,362),new BST.Node(null,null,863),511),288),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,463),95),new BST.Node(null,new BST.Node(null,null,892),679),590),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,701),97),null,823),null,842),null,994),
            new BST.Node(new BST.Node(new BST.Node(null,null,64),null,157),new BST.Node(null,new BST.Node(null,null,357),307),275),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,283),null,349),156),new BST.Node(null,null,957),849),
            new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(null,null,888),746),new BST.Node(null,null,935),891),46),
            new BST.Node(new BST.Node(new BST.Node(null,null,35),new BST.Node(new BST.Node(null,null,696),null,742),308),null,777),
            new BST.Node(new BST.Node(null,null,229),new BST.Node(new BST.Node(null,new BST.Node(null,null,708),695),null,770),370),
            new BST.Node(new BST.Node(new BST.Node(null,null,206),null,340),new BST.Node(null,new BST.Node(null,null,998),935),440),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,199),102),null,528),new BST.Node(null,null,709),587),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,33),null,38),null,260),new BST.Node(null,null,805),691),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,266),null,315),181),new BST.Node(null,null,709),432),
            new BST.Node(new BST.Node(new BST.Node(null,null,102),null,138),new BST.Node(null,new BST.Node(null,null,883),607),196),
            new BST.Node(null,new BST.Node(new BST.Node(new BST.Node(null,null,345),new BST.Node(null,null,718),417),null,732),267),
            new BST.Node(new BST.Node(null,null,268),new BST.Node(new BST.Node(null,new BST.Node(null,null,979),836),null,991),494),
            new BST.Node(new BST.Node(null,null,374),new BST.Node(new BST.Node(null,new BST.Node(null,null,800),763),null,883),698),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,940),889),new BST.Node(null,new BST.Node(null,null,972),969),954),
            new BST.Node(new BST.Node(null,null,150),new BST.Node(new BST.Node(null,null,268),new BST.Node(null,null,831),676),158),
            new BST.Node(null,new BST.Node(null,new BST.Node(new BST.Node(null,null,720),new BST.Node(null,null,946),750),675),666),
            new BST.Node(null,new BST.Node(new BST.Node(new BST.Node(null,null,24),new BST.Node(null,null,786),608),null,939),19),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,97),78),new BST.Node(null,null,869),239),null,941),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,647),576),new BST.Node(null,new BST.Node(null,null,962),958),648),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,98),new BST.Node(null,null,848),509),null,911),null,939),
            new BST.Node(new BST.Node(new BST.Node(null,null,502),null,561),new BST.Node(null,new BST.Node(null,null,925),699),584),
            new BST.Node(new BST.Node(null,null,134),new BST.Node(null,new BST.Node(new BST.Node(null,null,251),null,770),166),165),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,386),217),new BST.Node(null,new BST.Node(null,null,799),498),415),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,651),20),new BST.Node(null,null,853),842),null,903),
            new BST.Node(new BST.Node(null,null,192),new BST.Node(new BST.Node(null,null,329),new BST.Node(null,null,703),405),210),
            new BST.Node(null,new BST.Node(null,new BST.Node(null,new BST.Node(new BST.Node(null,null,708),null,821),668),153),106),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,158),136),null,229),new BST.Node(null,null,885),303),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,236),28),null,608),new BST.Node(null,null,764),641),
            new BST.Node(new BST.Node(null,null,63),new BST.Node(new BST.Node(null,null,681),new BST.Node(null,null,989),776),371),
            new BST.Node(new BST.Node(new BST.Node(null,null,167),null,204),new BST.Node(new BST.Node(null,null,495),null,610),294),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,213),null,444),21),new BST.Node(null,null,970),905),
            new BST.Node(new BST.Node(null,null,333),new BST.Node(null,new BST.Node(null,new BST.Node(null,null,763),629),524),495),
            new BST.Node(new BST.Node(new BST.Node(null,new BST.Node(null,null,206),66),new BST.Node(null,null,323),306),null,608),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,31),new BST.Node(null,null,463),98),null,778),null,916),
            new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,649),null,759),289),null,969),3),
            new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(null,null,519),163),new BST.Node(null,null,809),527),102),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,394),new BST.Node(null,null,620),452),266),null,989),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,310),null,423),null,477),new BST.Node(null,null,998),905),
            new BST.Node(null,new BST.Node(new BST.Node(null,null,268),new BST.Node(new BST.Node(null,null,562),null,714),486),249),
            new BST.Node(new BST.Node(new BST.Node(null,null,270),new BST.Node(null,null,499),367),new BST.Node(null,null,563),503),
            new BST.Node(new BST.Node(null,null,197),new BST.Node(null,new BST.Node(new BST.Node(null,null,784),null,809),555),199),
            new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(null,null,462),457),new BST.Node(null,null,911),773),165),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,305),new BST.Node(null,null,769),625),268),null,882),
            new BST.Node(new BST.Node(new BST.Node(null,null,132),null,256),new BST.Node(new BST.Node(null,null,675),null,900),669),
            new BST.Node(new BST.Node(null,null,81),new BST.Node(null,new BST.Node(new BST.Node(null,null,887),null,927),815),372),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,79),53),new BST.Node(null,new BST.Node(null,null,894),856),421),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,328),null,347),268),new BST.Node(null,null,849),406),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,207),null,534),null,613),new BST.Node(null,null,974),847),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(null,null,323),154),null,789),10),null,869),
            new BST.Node(new BST.Node(null,null,429),new BST.Node(new BST.Node(null,null,733),new BST.Node(null,null,973),836),504),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,308),161),new BST.Node(new BST.Node(null,null,604),null,673),540),
            new BST.Node(new BST.Node(null,null,224),new BST.Node(new BST.Node(null,null,425),new BST.Node(null,null,850),777),308),
            new BST.Node(null,new BST.Node(null,new BST.Node(new BST.Node(null,null,283),new BST.Node(null,null,911),389),173),140),
            new BST.Node(new BST.Node(new BST.Node(new BST.Node(null,null,493),new BST.Node(null,null,748),504),null,759),null,851),
            new BST.Node(new BST.Node(new BST.Node(null,null,756),new BST.Node(null,new BST.Node(null,null,894),854),840),null,961),
            new BST.Node(new BST.Node(null,null,249),new BST.Node(new BST.Node(null,null,414),new BST.Node(null,null,778),423),312),
            new BST.Node(new BST.Node(null,new BST.Node(null,null,275),155),new BST.Node(null,new BST.Node(null,null,978),845),794),
            new BST.Node(new BST.Node(null,null,173),new BST.Node(new BST.Node(null,null,608),new BST.Node(null,null,963),884),243),
            new BST.Node(new BST.Node(null,null,97),new BST.Node(new BST.Node(null,new BST.Node(null,null,809),528),null,918),387),
            new BST.Node(null,new BST.Node(new BST.Node(new BST.Node(null,null,375),null,536),new BST.Node(null,null,920),846),353),
            new BST.Node(new BST.Node(new BST.Node(null,null,47),new BST.Node(null,new BST.Node(null,null,644),467),224),null,800),
            new BST.Node(null,new BST.Node(new BST.Node(null,new BST.Node(null,null,717),441),new BST.Node(null,null,914),912),431),
            new BST.Node(new BST.Node(null,null,22),new BST.Node(null,new BST.Node(null,new BST.Node(null,null,836),524),405),100),
            new BST.Node(new BST.Node(new BST.Node(null,null,51),null,90),new BST.Node(null,new BST.Node(null,null,680),295),109),
            new BST.Node(new BST.Node(null,new BST.Node(null,new BST.Node(null,new BST.Node(null,null,740),569),311),276),null,917),
            new BST.Node(new BST.Node(null,null,43),new BST.Node(new BST.Node(null,new BST.Node(null,null,725),371),null,868),183),
            new BST.Node(new BST.Node(null,new BST.Node(new BST.Node(null,null,263),null,765),194),new BST.Node(null,null,850),794),
            new BST.Node(null,new BST.Node(new BST.Node(new BST.Node(null,null,618),null,671),new BST.Node(null,null,984),828),28),
            new BST.Node(null,new BST.Node(null,new BST.Node(new BST.Node(new BST.Node(null,null,555),null,647),null,672),483),304)    };

            @Test
            @Grade(value = 1, cpuTimeout = 1000)
            public void testEqual5() {
                for(int i = 0; i < bst5.length; i++) {
                    BST teacher = new BST();
                    teacher.root = bst5[i];
                    int[] preOrder = teacher.preorderWrite();
                    BST student = new BST(preOrder);
                    assertEquals(teacher, student);
                }
            }
    }
    
}
