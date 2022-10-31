package searching;
import static searching.RedBlackTreeConverter.RBNode;
import static searching.RedBlackTreeConverter.TwoThreeNode;
import static searching.RedBlackTreeConverter.Color;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


@RunWith(Enclosed.class)
public class RedBlackTreeConverterTest {

    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterized {

        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Arrays.asList(new Object[][] {
                            { "Ex1", new String[] {"S", "E", "A", "R", "C", "H", "X", "M", "P", "L"} },
                            { "Ex2", new String[] {"I", "N", "G", "O", "U", "S", "P", "E", "R", "F", "C", "T"} },
                            { "AllBlack", new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"} }, //all black
                            { "Ex3", new String[] {"E", "A", "I", "M", "D", "F", "L", "G", "B", "K", "H", "C", "J"} },
                            { "Small1", new String[] {"pddy", "nfgu", "mpsn", "eujq", "mebt", "lykm", "coai", "jzqj", "gzgq", "bjxr", "muwx", "elfs", "npds", "ucfq", "qsrg", "igue", "mqvt", "zfet", "ylvi", "cyzg"}},
                            { "Small2", new String[] {"osky", "sgiy", "acfd", "tlid", "szhe", "hfoo", "npfd", "bwzl", "lapk", "hfmn", "urgr", "xnpb", "hvys", "nhui", "gsqx", "btft", "yjwa", "cwtn", "vrzs", "ipsc"}},
                            { "Long1", new String[] {"hnlz", "tagb", "awmk", "pzkm", "vhuz", "rggw", "kvsv", "ecqf", "mvzo", "nkjp", "aylp", "qgsy", "zskx", "ijzy", "sqak", "bioi", "wtez", "eugm", "dozg", "huzg", "bjma", "qsvx", "bsxm", "tked", "njqa", "veep", "zjzs", "qtlh", "eyoi", "qsjg", "izud", "olxq", "wwui", "qdqr", "ljnn", "pveb", "qywv", "lgau", "atqv", "fuab", "vbbu", "znmp", "tsww", "kfuz", "sglt", "vbos", "iyun", "hivz", "ydop", "jufp", "tvzm", "cpaw", "jjmz", "fvnh", "ldzw", "xhtz", "ktyp", "hfqz", "kurg", "mevb", "uhtz", "tiul", "esez", "tadv", "upts", "mnrp", "szig", "wiot", "zona", "dmth", "zazg", "szei", "nrng", "ezvu", "npom", "hymp", "nzar", "usif", "ezrd", "tvrh", "dpqs", "dbid", "oxtm", "eare", "jzoy", "egjc", "dsrw", "yzwn", "vnqf", "kyvm", "uerw", "lwkz", "emfh", "abvu", "tsyv", "bxgi", "jdlo", "tryi", "iaup", "lsqi"}},
                            { "Long2", new String[] {"mcej", "swyh", "ozgd", "dmwa", "vxav", "xyec", "todw", "uhtr", "gvvq", "digz", "kaja", "ejdf", "zfja", "ftff", "gloa", "uxpa", "ihqh", "ozum", "sbzt", "dxfw", "vjxq", "qplc", "odkc", "ifak", "vhyq", "xrww", "crdh", "qmvs", "qvim", "mnul", "guif", "caoe", "zpkc", "varw", "pdim", "foom", "eusi", "nxuv", "xygi", "zybu", "arhm", "bjjj", "emzf", "powq", "zmce", "snep", "jega", "hguv", "bbys", "fkqc", "rrsm", "nqtx", "qpxu", "ijng", "zrhw", "zezz", "odvt", "drye", "mbbr", "pypv", "jaru", "dpnj", "sstm", "xvoq", "siqp", "plpz", "fcge", "issf", "nzci", "ptgu", "vxoc", "rawd", "ashp", "tixk", "gxch", "fzek", "pslo", "bzhe", "fyrc", "syru", "bwzr", "xxad", "kfre", "raqk", "yaqj", "dlel", "cfsg", "ncfo", "gswj", "mkyu", "gles", "bdfd", "nrkn", "fwup", "wrak", "seyw", "fuvm", "hjyw", "mlbe", "laaf", "wmfv", "qfsl", "bazk", "agba", "zceq", "gyct", "lmho", "koot", "bagl", "fxmc", "dkok", "waxx", "wwsu", "hpwx", "mbxa", "mdyc", "ihkc", "qswu", "hrva", "gtky", "yxdd", "vxie", "zbzi", "ftkk", "didk", "hkqi", "ouoc", "lsdy", "euwo", "sork", "bjkh", "ozep", "ysiw", "uvvo", "mcpi", "oouq", "vsll", "ttho", "cmpe", "yxrk", "rczo", "fgnh", "cujb", "iyuw", "snsn", "gnke", "kgqr", "yzxe", "ucze", "syoa", "ijmq", "mujg", "ydwb", "zrzo", "icdj", "ijmy", "tbbq", "okos", "ptmh", "fqwo", "oglg", "qelu", "ngvc", "kcbn", "eftf", "jyjn", "tvvv", "ewbu", "nfar", "uwgw", "qjqi", "dfoj", "jrau", "lxyj", "hlay", "qfih", "tlyv", "jhtk", "qkxx", "uxdo", "chla", "xmwb", "llbz", "jxja", "msvv", "trnn", "wuzn", "sgjm", "dpqb", "alrh", "dkuz", "ejii", "aaxv", "yfpx", "imsm", "voyx", "fnpu", "srcp", "qdkx", "iyfj", "egch", "wlbq", "vsvi", "lncr", "odxc", "mfel", "rqgm", "pter", "bqan", "ljxj", "wmsx", "dmud", "qhjv", "ruko", "ekig", "gopd", "bdcj", "tqez", "kmea", "tyhx", "tnoe", "bdro", "lorr", "uwel", "ubli", "irbk", "ylnw", "nbjd", "upua", "flro", "ufyx", "nsmg", "aeyj", "eoes", "dsqt", "npye", "wimq", "esgs", "ynaj", "gorc", "dmes", "ezbz", "pomx", "eipb", "rigb", "vvar", "njcf", "rqqc", "oyzk", "yxim", "hpmf", "ezrw", "oodu", "ggal", "dues", "cftp", "dsyd", "zort", "yozr", "hxjw", "skyt", "goai", "nggt", "ceuv", "lcek", "ubgy", "tmat", "fipl", "adzw", "iqcf", "lekq", "aavm", "ksfg", "mlnn", "usbn", "sgav", "feps", "tpwa", "vodp", "tewr", "wxdi", "nfcc", "euis", "kxwk", "jogc", "xlpq", "bdwl", "yzmx", "qaya", "czem", "rktg", "qdut", "zewo", "zfoc", "ezfl", "shsx", "ggqd", "jrhi", "azyx", "kdxo", "buly", "rvgr", "qhff", "wapx", "nmgw", "qvdq", "vgad", "fyzd", "wjqb", "ljks", "gdsi", "gekn", "bror", "emuw", "nfwn", "aqga", "qakr", "uzcy", "bdvi", "alwo", "ggnh", "czfj", "danq", "aaup", "zveb", "vlcy", "jqep", "dpja", "qlij", "ikvx", "wyqu", "ggcp", "kxjp", "mxpz", "eotv", "yfwn", "njiy", "glde", "ywok", "xnaj", "aflj", "uufp", "jzba", "qkxj", "vysi", "ilkd", "afgt", "lrcx", "ezwx", "svhy", "tclb", "naub", "qkeh", "svib", "vmmu", "qdip", "jjnz", "mnhy", "utmx", "twgz", "rxvz", "tdfp", "ycrp", "zbur", "ywtd", "uzpm", "bbnj", "wkfd", "hfdq", "jmky", "svdj", "gohw", "nwim", "uzxd", "npdb", "ipfl", "zsij", "bthe", "flsc", "xxwv", "tsip", "rkat", "dnil", "wmtv", "hluo", "knra", "smkd", "ehuj", "rkas", "dipn", "hhlu", "ziim", "kqek", "zeqy", "iqmj", "uaoq", "egmp", "lytq", "frwd", "xehu", "iovr", "nire", "pppk", "khwu", "pfhd", "cdsz", "jxua", "ekon", "dbnl", "hhwa", "mzao", "npds", "hqln", "pkwi", "unjz", "jcot", "bckf", "zlsh", "vhgn", "eect", "qhhb", "qjbq", "tvdi", "bjua", "cnzf", "tcpf", "bvba", "sfbz", "kddx", "dczn", "fmmp", "shlk", "kqiq", "zkcs", "hdxe", "kjkt", "wgfq", "twac", "myyl", "ywju", "rune", "awlm", "gipf", "vcni", "evzn", "hxtg", "gzyv", "okoq", "dxaf", "tvvu", "fhzc", "mvdr", "itep", "iyhd", "vhyl", "qrkw", "pijr", "qyxm", "qsns", "tzjl", "zasd", "jsja", "rnme", "tube", "hrvr", "sypm", "ihjm", "gise", "clol", "ydnv", "fdqw", "gfyu", "aqjb", "qikn", "zcmu", "clzl", "ofza", "itds", "xppv", "ebsr", "umoo", "hqsw", "yans", "nrtl", "wlly", "puve", "zonc", "polk", "ysss", "apeu", "nugy", "npff", "gjpk", "jxmc", "jekx", "efgr", "ovid", "ztsv", "ymxu", "hjmm", "rnvl", "tsqf", "bipa", "lqxr", "lder", "vhvk", "bmbb", "pseu", "ioko", "xoaq", "efbi", "bfan", "xeka", "nltd", "wbmk", "wjdg", "ofzv", "hsbt", "wykz", "urwj", "tuob", "laul", "esiu", "zwqy", "ppmg", "obmm", "ojmi", "peqn", "kman", "bzei", "iluf", "dyed", "tnzc", "pksh", "ivgu", "rknv", "bcbo", "ymaz", "ahqb", "blxl", "enpm", "guan", "vqih", "lvmx", "ryey", "pttj", "xnyc", "ydqm", "uxig", "nbah", "sxdb", "wmyh", "emsg", "gofc", "fqly", "hxfj", "yaof", "epmq", "ocpk", "kuax", "kfvq", "dpxy", "nsxd", "xnln", "gnpx", "kxll", "lqxt", "ncbg", "xdwg", "slgz", "ljzn", "icrk", "ppgo", "ofxd", "ftuh", "tvfa", "zqpl", "vuyl", "upnx", "icpn", "kfzk", "ahuv", "tpwy", "fmvu", "hwta", "owtw", "glyu", "tyst", "pibo", "qxac", "okzl", "evyl", "edaw", "jaun", "anqy", "kpxi", "nerv", "oaoy", "gprq", "omsy", "kakz", "tlrc", "auhc", "aqes", "frcp", "lupn", "xxgx", "rtkm", "sufx", "qggj", "cbjz", "jhjh", "ucju", "spgm", "nhzt", "zkdy", "xxwt", "oxko", "zxrg", "wtvn", "xshr", "frix", "jcct", "juiv", "tsow", "ihdk", "efma", "zvxg", "xyee", "vnzv", "bylw", "gtft", "qbmx", "wcxu", "wcse", "sotz", "icla", "hqqy", "nkeu", "pqvx", "weoc", "pfpr", "qgio", "wpwe", "udmf", "vitm", "faxr", "cfhg", "ldaq", "wlys", "lyzg", "midi", "xxlr", "biby", "akit", "ukcb", "tkiu", "hdyb", "ssrl", "xfwm", "hhmw", "bspq", "rxhb", "ldfs", "uthw", "wimy", "ltsc", "mnxr", "gyju", "kppl", "vxkx", "ldgf", "rddv", "yiga", "dkmc", "ynnc", "uccg", "cejg", "jomh", "ipvk", "cawj", "ifet", "vprz", "frkc", "mghy", "igcr", "uzkc", "dwgs", "dvad", "gasp", "fjyf", "zrcj", "gwtm", "mzih", "gvlj", "ndya", "xpoj", "cffj", "savi", "ykes", "ctgu", "lpub", "qvue", "wntb", "yfog", "vqcm", "ukxr", "fxhx", "zpjy", "qkjg", "fnau", "ahvq", "emju", "ikbh", "qdob", "huqq", "erlc", "mxwr", "gqik", "wmkw", "wxxv", "vczz", "wjgt", "quyi", "zqqs", "ihqz", "rkhy", "nhpz", "junb", "tylk", "rdlm", "qqxh", "sryn", "ozbn", "aqdl", "gvpz", "ncnl", "hfmw", "nomb", "uvqw", "rlip", "azxs", "lnjm", "onpk", "nean", "ihdg", "ysgx", "cpty", "ganq", "qlff", "lcrn", "tuov", "lwzc", "jien", "ydsp", "mvkj", "yofq", "tkbb", "ihlk", "arzp", "tlwo", "cbbo", "ummq", "kkeb", "mtno", "qmqu", "xmqt", "maiv", "bxce", "rgem", "gczi", "prmh", "kypz", "bsqn", "dugk", "zzkz", "gcgc", "hebi", "ftas", "tyla", "shtk", "rdyu", "ixfx", "enkm", "trnv", "qlio", "crud", "wbpk", "bxry", "warm", "lpwg", "ylcx", "zfew", "sqxa", "jsxp", "gbwp", "duro", "ompk", "mkuy", "zlgb", "kyrp", "tevf", "onqw", "hldt", "qzdo", "ugwc", "uvjp", "kwxb", "eijg", "wpmz", "dosu", "tnhx", "oqdv", "fuon", "wdlg", "amgo", "revc", "oybt", "xujg", "vfxk", "qzqe", "zhzy", "ival", "iiiw", "andy", "eqdj", "tosy", "ilpf", "dein", "coll", "vpmp", "ygtq", "tfgx", "jqqf", "imsj", "ppnn", "wlnv", "fuer", "zsrs", "hvjp", "ofkj", "qfka", "vyoy", "gzmf", "sssj", "hafc", "hiux", "nlna", "amel", "mjqv", "ydjd", "pbda", "iiiz", "cqck", "syqa", "ihoy", "zlox", "npfv", "aquw", "swdu", "asix", "makp", "vcng", "wnbr", "cscu", "xlpt", "zpcp", "dsgn", "rzpd", "hobh", "esnf", "dnvk", "xiwg", "dfhs", "aisl", "efuu", "aaxq", "fgdv", "sknw", "uisg", "jqjc", "dspe", "vdga", "udmm", "iyng", "qhsc", "pmvu", "nnxq", "yvbb", "ryyo", "ztdz", "qfbq", "vzqa", "qpja", "ihhk", "xsdg", "gcdk", "khwa", "lpna", "nwfu", "edzy", "uvyi", "knlh", "axtt", "mqaf", "wkkm", "ezxa", "fvha", "ekyz", "hjnu", "sdte", "msuw", "oydk", "hdhk", "jlad", "wers", "xaxi", "szhf", "hmnp", "kqwd", "keyf", "znwx", "pepf", "zgot", "ssno", "ygep", "rgyw", "azax", "oaok", "totw", "mrxb", "saph", "sfwn", "pneo", "fkiv", "dvgd", "mdrz", "lubg", "azaz", "elcv", "xmfw", "bojg", "mruq", "tgez", "eich", "ynsf", "udsc", "nnjw", "frty", "xjpn", "busb", "nlte", "kdap", "xqum", "ltwo", "enfh", "slaf", "rned", "ohde", "fwse", "mfhq", "bisl", "raln", "vnog", "aiwb", "fysp", "vknn", "gmwn", "pstu", "simi", "pqah", "qlzg", "odfl", "ynvv", "pjag", "bavn", "tjmd", "flug", "bfqo", "swbr", "ffef", "hsxa", "jutm", "gryr", "tdpt", "wpjp", "uvah", "hnpv", "njvw", "jjtt", "hidk", "vxzz", "ytyz", "noak", "thqo", "kmqu", "hzwi", "bvij", "lzul", "aesb"}}
                    });
        }

        final String [] keys;
        RBTChecker<String> checker;
        

        public TestParameterized(String _ignored, String [] keys) {
            this.keys = keys;
            TwoThreeNode<String> root = null;
            for (String key : keys) {
                root = insert(root, key, key.length());
            }
            this.checker = new RBTChecker<>(RedBlackTreeConverter.convert(root), root);
        }

        @Test
        @Grade(value = 10, cpuTimeout = 300)
        @GradeFeedback(message="Your tree does not contains all the initial keys")
        public void checkContent() {
            assertTrue(checker.checkKeys(keys));
        }

        @Test
        @Grade(value = 10, cpuTimeout = 300)
        @GradeFeedback(message="Your tree is not a BST")
        public void checkBST() {
            assertTrue(checker.isBST());
        }

        @Test
        @Grade(value = 10, cpuTimeout = 300)
        @GradeFeedback(message="The size of the nodes in the tree are wrong")
        public void checkSize() {
            assertTrue(checker.isSizeConsistent());
        }

        @Test
        @Grade(value = 10, cpuTimeout = 300)
        @GradeFeedback(message="Your RedBlackTree is not a 2-3 tree")
        public void check23() {
            assertTrue(checker.is23());
        }

        @Test
        @Grade(value = 10, cpuTimeout = 300)
        @GradeFeedback(message="Your tree is not balanced")
        public void checkBalanced() {
            assertTrue(checker.isBalanced());
        }

    }

    // ---- UTILIIIES FOR THE TESTS ----- //

    /**
     * Merges the node `nodeToInsert` into `nodeInto`. This assume that `nodeToInsert` is a 2-node that is
     * "going up" the tree after an insertion
     */
    private static<Key extends Comparable<Key>> TwoThreeNode<Key> addNodeInto(TwoThreeNode<Key> nodeInto, TwoThreeNode<Key> nodeToInsert) {
        // The main thing to observe is that nodeToInsert is the result of a merging or a failed insert. In that case, nodeToInsert is moving "up" from
        // one of the child of nodeInto.
        // Thus the properties of 2-3 nodes is still respected. If it comes from the left child, then all its key are less than nodeInto.leftKey. If it comes
        // from the centerChild then its key is comprised between the left and right key of nodeInto. Finally if it comes from the right child
        // then its key is greater than the keys of nodeInto.
        //
        // If `nodeInto` has no rightKey, then we can just create a 3-node depending if the key of `nodeToInsert`
        // is greater than the key currently in `nodeInto` or not.
        if (nodeInto.rightKey == null) {
            if (nodeInto.leftKey.compareTo(nodeToInsert.leftKey) < 0) {
                // The left key of the node to insert is greater than the other left key. So the `nodeToInsert` left key becomes the
                // right key of the 3-node
                nodeInto.rightKey = nodeToInsert.leftKey;
                nodeInto.rightValue = nodeToInsert.leftValue;

                nodeInto.centerChild = nodeToInsert.leftChild;
                nodeInto.rightChild = nodeToInsert.centerChild;

            } else {
                // In the other case, the left key of `nodeInto` becomes the right key
                nodeInto.rightKey = nodeInto.leftKey;
                nodeInto.rightValue = nodeInto.leftValue;
                nodeInto.rightChild = nodeInto.centerChild;
                
                nodeInto.leftKey = nodeToInsert.leftKey;
                nodeInto.leftValue = nodeToInsert.leftValue;

                nodeInto.centerChild = nodeToInsert.centerChild;
                nodeInto.leftChild = nodeToInsert.leftChild;

            }
            return nodeInto;
        }
        // nodeInto is a 3-node, with keys k1 < k2 < k3 and nodeToInsert is a 2-node with key k.
        // We must create a new 3-node and return a 2-node that will be merged in the parent
        if (nodeInto.leftKey.compareTo(nodeToInsert.leftKey) >= 0) {
            // First case, the left key of nodeInto is greater than the leftKey of nodeToInsert.
            // Note that since the left key of nodeToInsert is less than the left key of nodeInto, it means
            // that nodeToInsert is actually the left child of nodeInto (because it can not be move upward from the
            // center or right child)

            // The new node that will be move upward. It takes its left key/value from nodeInto. Its left child is nodeToInsert
            // (its value is less than nodeInto, the left key) and as rightChild nodeInto (that will be modified such that its value
            // are higher than the leftKey of node)
            TwoThreeNode<Key> node = new TwoThreeNode<>(nodeInto.leftKey, null, nodeInto.leftValue, null);
            node.leftChild = nodeToInsert;
            node.centerChild = nodeInto;

            // Modifications of nodeInto in order to respect the search tree invariants. nodeInto becomes a 2-node using its right key/value
            // so that it's higher than node
            nodeInto.leftChild = nodeInto.centerChild;
            nodeInto.centerChild = nodeInto.rightChild;
            nodeInto.leftKey = nodeInto.rightKey;
            nodeInto.leftValue = nodeInto.rightValue;
            nodeInto.rightKey = null;
            nodeInto.rightValue = null;
            nodeInto.rightChild = null;
            return node;
        } else if (nodeInto.rightKey.compareTo(nodeToInsert.leftKey) >= 0) {
            // In this case, the key of nodeToInsert is greater than the left key of nodeInto, but smaller than its right key.
            // Thus nodeInto will be split in two 2-nodes that will be the left child (for the left key) and right child (for the right key)
            // ot nodeToInsert. It will be returned for merging upstream

            // New 2-node to set as center child of the 2-node nodeToInsert
            TwoThreeNode<Key> node = new TwoThreeNode<>(nodeInto.rightKey, null, nodeInto.rightValue, null);
            node.leftChild = nodeToInsert.centerChild;
            node.centerChild = nodeInto.rightChild;
            nodeToInsert.centerChild = node;

            // The left child  of nodeToInsert has keys greater than the left key of nodeInto
            nodeInto.centerChild = nodeToInsert.leftChild;
            nodeToInsert.leftChild = nodeInto;
            // Making it a 2-node
            nodeInto.rightKey = null;
            nodeInto.rightValue = null;
            nodeInto.rightChild = null;
            return nodeToInsert;
        } else {
            // Last case, the key of nodeToInsert is greater than the keys of nodeInto.
            // In that case we splti again nodeInto into two 2-nodes, one of which will be returned for merging upward.

            // The node that will be returned. It takes the right key/value of nodeInto as left key.
            // Its left child will be the 2-node remaining from the splitting of nodeInto (for which the left key is indeed
            // smaller than rightKey).
            // Its right child will be nodeToInsert since its key are already greater than the right key of nodeInto
            TwoThreeNode<Key> node = new TwoThreeNode<>(nodeInto.rightKey, null, nodeInto.rightValue, null);
            node.leftChild = nodeInto;
            node.centerChild = nodeToInsert;

            // Making nodeInto a 2-node
            nodeInto.rightKey = null;
            nodeInto.rightValue = null;
            nodeInto.rightChild = null;
            return node;
        }
    }

    private static<Key extends Comparable<Key>> TwoThreeNode<Key> insert(TwoThreeNode<Key> root, Key key, int value) {
        if (root == null) {
            return new TwoThreeNode<>(key, null, value, null);
        }
        // If the key is already present in the tree, we update the value
        if (root.leftKey.compareTo(key) == 0) {
            root.leftValue = value;
            return root;
        } else if (root.rightKey != null && root.rightKey.compareTo(key) == 0) {
            root.rightValue = value;
            return root;
        }
        // If the current node is a leaf, then we add the key to the node, maybe create a 3 node
        if (root.isLeaf()) {
            TwoThreeNode<Key> node = new TwoThreeNode<>(key, null, value, null);
            return addNodeInto(root, node);
        }
        // Root is an internal node.
        // First case, the key we are trying to insert is below the key of the current node -> must be inserted in the left subtree
        // Second case, we are in a 3-node and the key is below the right key -> must insert in the center sub-tree
        // Last case, we need to insert in the right subtree
        if (key.compareTo(root.leftKey) < 0) {
            TwoThreeNode<Key> node = insert(root.leftChild, key, value);
            if (node == root.leftChild) {
                return root;
            }
            return addNodeInto(root, node);
        } else if (root.rightKey == null || key.compareTo(root.rightKey) < 0) {
            TwoThreeNode<Key> node = insert(root.centerChild, key, value);
            if (node == root.centerChild) {
                return root;
            }
            return addNodeInto(root, node);
        } else {
            TwoThreeNode<Key> node = insert(root.rightChild, key, value);
            if (node == root.rightChild) {
                return root;
            }
            return addNodeInto(root, node);
        }
    }

    private static class RBTChecker<Key extends Comparable<Key>> {
        private RBNode<Key> root;
        private TwoThreeNode<Key> root23;

        public RBTChecker(RBNode<Key> root, TwoThreeNode<Key> root23) {
            this.root = root;
            this.root23 = root23;
        }

        /**
         * Checks if `root` is a BST
         */
        public boolean isBST() {
            return isBST(root, null, null);
        }

        /**
         * Checks if the subtree rooted at `node` is a BST. This uses bounds to check
         * the validity of the keys.
         * The subtree rooted at `node` must have all its key in the interval ]min, max[
         * (if bounds not null).
         * 
         * In the recursive case, the bounds are updated using the key of the node. If the left
         * subtree is explored, then all keys must be less than the node's key (by definition of a BST)
         * and the upper bound is updated.
         * In the other case, the lower bound  is updated as all the keys in the subtree must be higher than
         * the node's key.
         *
         * @param node The root of the cheked subtree
         * @param min The current lower bound
         * @param max The current upper bound
         * @return true if and only if the tree rooted at node is a valid BST
         */
        private boolean isBST(RBNode<Key> node, Key min, Key max) {
            if (node == null) {
                return true;
            }
            if ((min != null && node.key.compareTo(min) <= 0) || (max != null && node.key.compareTo(max) >= 0)) {
                return false;
            }
            return isBST(node.leftChild, min, node.key) && isBST(node.rightChild, node.key, max);
        }

        public boolean isSizeConsistent() {
            return isSizeConsistent(root);
        }

        /**
         * Returns true if the size of the nodes in the tree are consistent
         *
         * @param node The root of the checked subtree
         */
        private boolean isSizeConsistent(RBNode<Key> node) {
            if (node == null) {
                return true;
            }
            int leftChildSize = node.leftChild == null ? 0 : node.leftChild.size;
            int rightChildSize = node.rightChild == null ? 0 : node.rightChild.size;
            if (node.size != leftChildSize + rightChildSize + 1) {
                return false;
            }
            return isSizeConsistent(node.leftChild) && isSizeConsistent(node.rightChild);
        }

        /**
         * Returns the rank of the key, that is the number if keys in the tree
         * strictly less than `key`
         */
        public int rank(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("parameter of rank() is null");
            }
            return rank(root, key);
        }

        public int rank(RBNode<Key> node, Key key) {
            if (node == null) {
                return 0;
            }
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                // If searched key is less than the node key, then all keys which are smaller
                // than key are in the left subtree
                return rank(node.leftChild, key);
            } else if (cmp > 0) {
                // if the searched key is higher than the nodes key, then all nodes in the left
                // subtree are already known to be smaller. But there might still have some in the
                // right subtree.
                // The + 1 account for the node which is itself less than the key
                return 1 + node.leftChild.size + rank(node.rightChild, key);
            } else {
                // The node have the same key, only the left subtree have smaller key
                return node.leftChild.size;
            }
        }

        private void collectNodes(ArrayList<Key> l, RBNode<Key> root) {
            if (root != null) {
                collectNodes(l, root.leftChild);
                l.add(root.key);
                collectNodes(l, root.rightChild);
            }
        }
        
        /**
         * Check that the rank are consistents in the tree
         */
        public boolean isRankConsistent() {
            ArrayList<Key> keys = new ArrayList<>();
            collectNodes(keys, root);
            for (int i = 0; i < keys.size(); i++) {
                if (rank(keys.get(i)) != i)
                    return false;
            }
            return true;
        }

        public boolean is23() {
            return is23(root);
        }

        /**
         * Returns true if and only if the subtree rooted at node is a correct 2-3 tree
         *
         * @param node the root of the checked subtree
         * @return true if and only if node is the root of a 2-3 tree
         */
        private boolean is23(RBNode<Key> node) {
            if (node == null) {
                return true;
            }
            // No right-leaning red edges
            if (node.rightChild != null && node.rightChild.isRed()) {
                return false;
            }
            // No more than 2 consecutive red link
            if (node != root && node.isRed() && node.leftChild != null && node.leftChild.isRed()) {
                return false;
            }
            return is23(node.leftChild) && is23(node.rightChild);
        }

        /**
         * Returns true if and only if the tree is balanced
         */
        public boolean isBalanced() {
            int expectedBlack = 0;
            RBNode<Key> node = root;
            while (node != null) {
                if (node.isBlack()) {
                    expectedBlack ++;
                }
                node = node.leftChild;
            }
            /*
            return isBalanced(root, expectedBlack);
            */
            TreeSet<Integer> e = new TreeSet<>();
            t(e, root, 0);
            return e.size() == 1;
        }

        private void t(TreeSet<Integer> s, RBNode<Key> root, int cur) {
            if (root == null) {
                s.add(cur);
            } else {
                if (root.isBlack()) {
                    t(s, root.leftChild, cur+1);
                    t(s, root.rightChild, cur+1);
                } else {
                    t(s, root.leftChild, cur);
                    t(s, root.rightChild, cur);
                }
            }
        }

        /**
         * Checks the number of black links from root to every of its leaves.
         * The method expects to find `blackToSee` number of black links. Thus
         * if a leaf is reached and `blackToSee` is 0, the visited branch is correct
         *
         * @param root The root of the checked subtree
         * @param blackToSee the number of expected black links from root to every of its leaf
         * @return true if and only if the subtree rooted at root is balanced
         */
        private boolean isBalanced(RBNode<Key> root, int blackToSee) {
            if (root == null) {
                return blackToSee == 0;
            }
            int stillToSee = root.isBlack() ? blackToSee - 1 : blackToSee;
            return isBalanced(root.leftChild, stillToSee) && isBalanced(root.rightChild, stillToSee);
        }

        /**
         * Check that the tree contains exactly the expected keys
         *
         * @param expectedKeys the keys to have in the tree
         */
        public boolean checkKeys(Key [] expectedKeys) {
            TreeSet<Key> set = new TreeSet<>();
            for (Key k : expectedKeys) {
                set.add(k);
            }
            ArrayList<Key> treeContent = new ArrayList<>(root.size);
            collectNodes(treeContent, root);
            for (Key k : treeContent) {
                if (!set.contains(k)) {
                    //return false;
                }
                set.remove(k);
            }
            return set.isEmpty();
        }
    }

}
