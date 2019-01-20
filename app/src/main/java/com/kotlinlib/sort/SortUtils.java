package com.kotlinlib.sort;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SortUtils {

    /**
     * 对中文数组按拼音进行排序
     * @param strings
     * @return
     */
    public static List<String>sort(List<String> strings){
        Comparator<Object> com = Collator.getInstance(Locale.ENGLISH);
//        CEComplexComparator com = new CEComplexComparator();
        Collections.sort(strings, com);
        return strings;
    }


    static class CEComplexComparator implements Comparator<String> {
        public int compare(String str1, String str2) {
            char[] c={str1.toLowerCase().charAt(0),str2.toLowerCase().charAt(0)};//首字母
            String[] str={str1.substring(0, 1),str2.substring(0, 1)};
            int type[]={1,1};
            for(int i=0;i<2;i++)
            {
                if(str[i].matches("[\\u4e00-\\u9fbb]+"))//中文字符
                    type[i]=1;
                else if(c[i]>='a' && c[i]<='z')
                    type[i]=2;
                else if(c[i]>='1' && c[i]<='9')
                    type[i]=3;
                else
                    type[i]=4;
            }
            if(type[0]==1 && type[1]==1)
                return Collator.getInstance(java.util.Locale.CHINESE).compare(str1, str2);
            if(type[0]==type[1]) //同一类
                return str1.compareTo(str2);
            return type[0]-type[1];
        }
    }

    /**
     * 得到 全拼
     *
     * @param src
     * @return
     */
//    public static String getPingYin(String src) {
//        char[] t1;
//        t1 = src.toCharArray();
//        String[] t2;
//        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
//        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
//        String t4 = "";
//        int t0 = t1.length;
//        try {
//            for (int i = 0; i < t0; i++) {
//                // 判断是否为汉字字符
//                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
//                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
//                    t4 += t2[0];
//                } else {
//                    t4 += java.lang.Character.toString(t1[i]);
//                }
//            }
//            return t4;
//        } catch (BadHanyuPinyinOutputFormatCombination e1) {
//            e1.printStackTrace();
//        }
//        return t4;
//    }

    /**
     * 得到中文首字母
     *
     * @param str
     * @return
     */
//    public static String getPinYinHeadChar(String str) {
//
//        String convert = "";
//        for (int j = 0; j < str.length(); j++) {
//            char word = str.charAt(j);
//            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
//            if (pinyinArray != null) {
//                convert += pinyinArray[0].charAt(0);
//            } else {
//                convert += word;
//            }
//        }
//        return convert;
//    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

}
