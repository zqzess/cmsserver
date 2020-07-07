/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author zqzess
 */
public class getLength {
    static String[] Weekarrtmp={"一","二","三","四","五","六","日"};

    public static void main(String[] args) {
        String[] arrStrings={"你好","不好","再见"};
        
        String[] arr={"",""};
        String[] array={null,null,null};
        String a="周一1-2";
        String b="周四4-5";
        String c="周一1-2,周四4-5";
        String d="";
        String e=null;
        int length=arrStrings.length;
        int length2=arr.length;
//        System.out.println(a.length());
//        System.out.println(b.length());
//        System.out.println(c.length());
        System.out.println(d.length());
        System.out.println(array.length);
String tmp="周一1-4";
String tmp2="周一1-4,周二1-2";
String tmp3="周一1-4,周二1-2,周三7-8";

        timeChange(tmp);
        timeChange(tmp2);
        timeChange(tmp3);
    }
    
    public static int weekBackChange(String arr)
    {
        int weektmp=0;
        for (int i=0;i<Weekarrtmp.length;i++)
        {
            if(Weekarrtmp[i].equals(arr))
            {
                weektmp=i+1;
            }
        }
        return weektmp;
    }
    public static String timeChange(String arr)
    {
        String time = "";
        if(arr.length()==5)
        {
            String lessonto = arr.substring(arr.indexOf('-')+1);
            String tmp=arr.substring(arr.indexOf("周")+1,arr.indexOf("-"));
            String lessfrom=tmp.substring(tmp.length()-1);
            String weekTmp=tmp.substring(0,1);
            int week=weekBackChange(weekTmp);
            time=week+","+lessfrom+","+lessonto;
            System.out.println("1:"+time);
        }else
        {
            String[] arrString=arr.split(",");
            for(int i=0;i<arrString.length;i++)
            {
                String lessonto = arrString[i].substring(arrString[i].indexOf('-')+1);
                String tmp=arrString[i].substring(arrString[i].indexOf("周")+1,arrString[i].indexOf("-"));
                String lessfrom=tmp.substring(tmp.length()-1);
                String weekTmp=tmp.substring(0,1);
                int week=weekBackChange(weekTmp);
                time=time+week+","+lessfrom+","+lessonto+",";
            }
            time=time.substring(0,time.length()-1);
            System.out.println("2:"+time);
        }


        return time;
    }
}
