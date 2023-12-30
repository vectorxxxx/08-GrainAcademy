package xyz.funnyboy.commonutils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 随机数工具类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 15:41:37
 */
public class RandomUtil
{
    private static final Random random = new Random();

    private static final DecimalFormat FOURDF = new DecimalFormat("0000");

    private static final DecimalFormat SIXDF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOURDF.format(random.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return SIXDF.format(random.nextInt(1000000));
    }

    /**
     * 给定数组，抽取n个数据
     *
     * @param list
     * @param n
     * @return
     */
    public static List<String> getRandom(List<String> list, int n) {
        Random random = new Random();

        HashMap<Object, Object> hashMap = new HashMap<>();

        // 生成随机数字并存入HashMap
        for (int i = 0; i < list.size(); i++) {
            int number = random.nextInt(100) + 1;
            hashMap.put(number, i);
        }

        // 从HashMap导入数组
        Object[] robjs = hashMap.values()
                                .toArray();

        List<String> r = new ArrayList<>();

        // 遍历数组并打印数据
        for (int i = 0; i < n; i++) {
            r.add(list.get((int) robjs[i]));
            System.out.print(list.get((int) robjs[i]) + "\t");
        }
        System.out.print("\n");
        return r;
    }
}
