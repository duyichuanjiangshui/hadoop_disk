package com.liangrui.hadoop_disk.util;

import com.liangrui.hadoop_disk.config.hadoop.conn.HbaseConn;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HbaseUtil {
    private static final Logger log = LogManager.getLogger(HbaseUtil.class);
    private static Connection con = null;
    static {
        try {
            con = HbaseConn.getConn();
        } catch (Exception ex) {
            log.error("初始化异常");
            ex.printStackTrace();
        }
    }
    /**
     * 保存
     */
    public static void insertHbaseData(String rowKey, String val) throws Exception {
        Table table = null;
        String colName = "data";//列簇
        String tableName = "file";//表名
        try {
            table = con.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(colName), Bytes.toBytes(rowKey), Bytes.toBytes(val));
            table.put(put);
        } finally {
            if (table != null)
                table.close();
        }
    }
    /**
     *查询
     */
    public static String getData(String rowkey) throws IOException {
        Map returnMap = new HashMap();
        String colName = "data";
        String tableName = "file";
        Table table = null;
        try {
            table = con.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowkey));
            get.addColumn(Bytes.toBytes(colName), Bytes.toBytes(rowkey));
            Result r = table.get(get);
            Cell[] cells = r.rawCells();
            for (Cell cell : cells) {
                returnMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
            }
        } finally {
            if (table != null)
                table.close();
        }
        return returnMap.get(rowkey)==null?null:returnMap.get(rowkey).toString();
    }
}
