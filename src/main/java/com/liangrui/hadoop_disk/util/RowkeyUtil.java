package com.liangrui.hadoop_disk.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Calendar;
import java.util.UUID;

public class RowkeyUtil {
    /*
    * 用Hash散列来替代随机Salt前缀的好处是能让一个给定的行有相同的前缀，这在分散了Region负载的同时，使读操作也能够推断。确定性Hash(比如md5后取前4位做前缀)能让客户端重建完整的RowKey，可以使用get操作直接get想要的行。
例如将上述的原始Rowkey经过hash处理，此处我们采用md5散列算法取前4位做前缀，结果如下
9bf0-abc001 （abc001在md5后是9bf049097142c168c38a94c626eddf3d，取前4位是9bf0）
7006-abc002
95e6-abc003
若以前4个字符作为不同分区的起止，上面几个Rowkey数据会分布在3个region中。实际应用场景是当数据量越来越大的时候，这种设计会使得分区之间更加均衡。
如果Rowkey是数字类型的，也可以考虑Mod方法。

    * */
    //所以我的设计是前四位用hush 散列 之后的用年月日时分秒
    public String getRowkey()
    {
        Calendar calendar=Calendar.getInstance();

        DateUtil dateUtil=new DateUtil();
        String last=dateUtil.getYear(calendar)+dateUtil.getMonth(calendar)+
                dateUtil.getDay(calendar)+dateUtil.getHour(calendar)+dateUtil.getminute(calendar)
                +dateUtil.getSecond(calendar);

       String first=DigestUtils.md5Hex(last);
       return first.substring(0,4)+last+UUID.randomUUID().toString().substring(0,4);
    }

}
