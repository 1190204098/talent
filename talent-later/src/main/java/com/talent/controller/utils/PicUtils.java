package com.talent.controller.utils;

import com.talent.domain.Page;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author jmj
 * @since 2021/12/16 13:49
 */
@Slf4j
public class PicUtils {
    /**
     * 默认的图片路径
     */
    public static final String DEFAULT_URL = WebUtils.getSession().getServletContext().getRealPath("/uploads/index/");
    /**
     * 服务器ip地址
     */
    public static String ip =getIpAddress();
    /**
     * 服务器端口
     */
    public static int port = WebUtils.getRequest().getServerPort();

    /**
     * 获取指定路径下图片
     * @param path
     * @param pageNo
     * @param pageSize
     * @return com.talent.domain.Page<java.lang.String> 图片链接地址
     * @author jmj
     * @since 16:42 2021/12/16
     **/
    public static Page<String> getPicsFromLocal(String path, int pageNo, int pageSize) {
        log.info("getPicsFromLocal() called with parameters => [path = {}],[pageNo = {}],[pageSize = {}]", path, pageNo, pageSize);
        //获取图片所在文件夹
        File file = new File(path);
        //图片分页类
        Page<String> page = new Page<>();
        //存储图片url集合
        List<String> res = new ArrayList<>();
        //图片访问基本路径
        String BASE_URL =  "http://" + ip + ":" + port + "/uploads/index/";

        //如果目录不存在, 表示没有图片, 则返回空数据的page
        if (!file.exists()) {
            log.info("目录[{}]未找到", path);
            return page;
        }
        //获取目录下的所有文件
        File[] pics = file.listFiles();
        //如果目录下没有图片, 返回空数据的page
        if (pics == null || pics.length == 0) {
            log.info("目录[{}]下无图片", path);
            return page;
        }
        //如果分页下标超出图片数量, 同样返回空数据的page
        if ((pageNo - 1) * pageSize > pics.length) {
            log.info("超出界限,startNo:{},total:{}", (pageNo - 1) * pageSize, pics.length);
            return page;
        }
        //如果图片每页大小超出图片总数量, 则改变每页大小
        pageSize = Math.min(pageSize, pics.length);
        //将指定页的图片路径封装到res图片url集合
        for (int i = (pageNo - 1) * pageSize; i < (Math.min(pageNo * pageSize, pics.length)); i++) {
            String url = BASE_URL + pics[i].getName();
            res.add(url);
        }
        //将查询出的结果和基本数据封装到page
        page.setRecords(res);
        page.setTotal(pics.length);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        log.info("首页图片获取成功");
        return page;
    }

    /**
     * 根据图片名称删除指定路径下的图片
     * @param path
     * @param name
     * @return boolean
     * @author jmj
     * @since 16:42 2021/12/16
     **/
    public static boolean deletePic(String path, String name) {
        log.info("deletePic() called with parameters => [path = {}],[name = {}]", path, name);
        File file = new File(path + name);
        if (file.delete()) {
            log.info("图片[{}]删除成功", path + name);
            return true;
        } else {
            log.info("图片[{}]删除失败", path + name);
            return false;
        }
    }

    /**
     * 获取服务器IP地址
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            //遍历网卡, 找到在使用的网卡
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    //找出在使用的网卡的ipv4地址, 找到则返回
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e);
        }
        return "";
    }
}
