package com.dp.common.utils.util;

public class EmojiUtil {
	
	private static String[] codeArr=new String[]{"/::)","/::~","/::B","/:8-)","/::<","/::$","/::-|","/::@","/::P","/::D",
												 "/::O","/::+","/:--b","/:,@P","/::d","/:,@o","/::L","/::>","/::,@","/:?",
												 "/:,@x","/:,@!","/:!!!","/:xx","/:bye","/:wipe","/:dig","/:handclap","/:&-(","/:B-)",
												 "/:<@","/:@>","/:>-|","/:X-)","/:pd","/:pig","/:strong","/:share","/:v","/:ok",
												 "/::|","/::X","/::Z","/::'(","/::(","/::Q","/::T","/:,@-D","/::g","/:|-)",
												 "/::!","/:,@f","/::-S","/:,@@","/::8","/::-O","/:P-(","/::'|","/::*","/:@x",
												 "/:8*","/:<W>","/:beer","/:basketb","/:oo","/:coffee","/:eat","/:rose","/:fade","/:showlove",
												 "/:heart","/:break","/:cake","/:li","/:bome","/:kn","/:footb","/:ladybug","/:shit","/:moon",
												 "/:sun","/:gift","/:hug","/:weak","/:@)","/:jj","/:@@","/:bad","/:lvu","/:no",
												 "/:love","/:<L>","/:jump","/:shake","/:<O>","/:circle","/:kotow","/:turn","/:skip","/:&>",
												 "/:#-0","/:hiphot","/:kiss","/:<&","/:oY"};
	private static String[] strArr=new String[]{"weixiao","piezui","se","deyi","liulei","haixiu","ganga","fanu","tiaopi","ciya",
												 "jingya","ku","lenghan","touxiao","baiyan","aoman","liuhan","hanxiao","xiuxian","yiwen",
												 "xu","shuai","kulou","qiaoda","zaijian","cahan","koubi","guzhang","qiudale","huaixiao",
												 "zuohengheng","youhengheng","bishi","yinxian","caidao","zhutou","qiang","woshou","shengli","OK",
												 "fadai","bizui","shui","daku","nanguo","zhuakuang","tu","yukuai","jie","kun",
												 "jingkong","fendou","zhouma","yun","fengle","haqian","weiqu","kuaikule","qinqin","xia",
												 "kelian","xigua","pijiu","lanqiu","pingpang","kafei","fan","meigui","diaoxie","zuichun",
												 "aixin","xinsui","dangao","shandian","zhadan","dao","zuqiu","piaochong","bianbian","yueliang",
												 "taiyang","liwu","yongbao","ruo","baoquan","gouyin","quantou","chajing","aini","NO",
												 "aiqing","feiwen","tiaotiao","fadou","ouhuo","zhuanquan","ketou","huitou","tiaoshen","youtaiji",
												 "jidong","luanwu","xianwen","zuotaiji","touxiang"};
	public static String codeToEmoji(String code){
		for(int i=0;i< codeArr.length;i++){
			String str=codeArr[i];
			//<img src="/static/images/admin/OK.png">
			String replace="<img src=\"/static/images/admin/wx/qq/"+strArr[i]+".gif\">";
			code=code.replace(str, replace);
		}
		return code;
	}
	public static String emojiToCode(String emoji){
		for(int i = 0; i < strArr.length; i++){
			String str = strArr[i];
			String str1 = "<img src=\"/static/images/admin/wx/qq/"+strArr[i]+".gif\">";
			emoji = emoji.replace(str1, codeArr[i]);
		}
		return emoji;
	}
	public static void main(String[] args) {
		String code="来个反馈<img src=\"/static/images/admin/wx/qq/weixiao.gif\">的反馈啦吃啊lkmfa<img src=\"/static/images/admin/wx/qq/weixiao.gif\">恐龙当家奥索福兰<img src=\"/static/images/admin/wx/qq/shengli.gif\">";
		System.out.println(emojiToCode(code));
	}
}
