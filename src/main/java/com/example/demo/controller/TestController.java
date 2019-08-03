package com.example.demo.controller;

import com.example.demo.ResultDO;
import com.example.demo.ResultDao;
import com.example.demo.TestDO;
import com.example.demo.config.MahoutConfig;
import com.example.demo.mapper.TestDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class TestController {

    /*
    * 5201760370纳秒(43.6万数据生成data文件耗费5秒多,6.54 MB)
    * 分析一个用户推荐文章耗费496321161纳秒（0.49秒）
    * 统计分析结果其实是不怎么现实，一个0.49秒，如果你有10w粉丝，统计下来就是13小时多了。
    * 那开多线程去跑呢？10w粉丝，由于是cpu密集型，线程数跟cpu核心数一样4个，也得跑3个多小时。
    */

    /*@Resource(name = "fileDataModel")
    DataModel dataModel;*/

    @Resource
    TestDao testDao;

    @Resource
    ResultDao resultDao;

    @GetMapping("/tongji")
    public long didSomething() throws IOException, TasteException {
        long time = System.nanoTime();
        URL url = MahoutConfig.class.getClassLoader().getResource("mahout/dajitui.data");
        DataModel dataModel = new FileDataModel(new File(url.getFile()));
        // 相似度计算（皮尔森相似度）
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);

        // 设置相似用户阈值（或使用NearestNUserNeighborhood）
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);

        for (int i = 0; i <= 435995; i += 5) {
            // 基于以上数据创建推荐器（这里使用的是基于用户的推荐，还有GenericItemBasedRecommender等推荐器）
            Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            List<RecommendedItem> recommendItems = null;
            try {
                recommendItems = recommender.recommend(i, 5);
            } catch (Exception e) {
                log.error("查询出错：{}", e.getMessage());
            }

            if (!CollectionUtils.isEmpty(recommendItems)) {
                List<ResultDO> resultDOList = new LinkedList<>();
                for (RecommendedItem recommendedItem : recommendItems) {
                    ResultDO resultDO = new ResultDO();
                    resultDO.setUid(i);
                    resultDO.setArticleid(recommendedItem.getItemID());
                    resultDO.setValue(new BigDecimal((recommendedItem.getValue())));
                    resultDOList.add(resultDO);
                }
                if (!CollectionUtils.isEmpty(resultDOList)) {
                    resultDao.saveAll(resultDOList);
                }
            }
        }
        long time1 = System.nanoTime();
        return time1 - time;
    }

    @GetMapping("/mahout")
    public List getSomething(int uid) throws TasteException, IOException {
        long time = System.nanoTime();
        URL url = MahoutConfig.class.getClassLoader().getResource("mahout/dajitui.data");
        DataModel dataModel = new FileDataModel(new File(url.getFile()));
        // 相似度计算（皮尔森相似度）
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);

        // 设置相似用户阈值（或使用NearestNUserNeighborhood）
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);

        // 基于以上数据创建推荐器（这里使用的是基于用户的推荐，还有GenericItemBasedRecommender等推荐器）
        Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        List<RecommendedItem> recommendItems = recommender.recommend(uid, 5);
        if (!CollectionUtils.isEmpty(recommendItems)) {
            for (RecommendedItem recommendedItem : recommendItems) {
                ResultDO resultDO = new ResultDO();
                resultDO.setUid(5);
                resultDO.setArticleid(recommendedItem.getItemID());
                resultDO.setValue(new BigDecimal((recommendedItem.getValue())));
                resultDao.save(resultDO);
            }
            long time1 = System.nanoTime();
            System.out.println(time1 - time);
            return recommendItems;
        }
        long time1 = System.nanoTime();
        System.out.println(time1 - time);
        return new ArrayList();
    }

    @GetMapping("/insert")
    public long doSomething() throws IOException {
        long time = System.nanoTime();
        File file = new File(MahoutConfig.class.getClassLoader().getResource("mahout").getPath() + "/dajitui.data");
        if (!file.exists()) {
            file.createNewFile();
        }
        List<TestDO> testDOList = Optional.ofNullable(testDao.findAll()).orElse(new ArrayList<>());
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        for (TestDO testDO : testDOList) {
            String msg = testDO.getUid() + "," + testDO.getIid() + "," + testDO.getScore() + "\n";
            fileOutputStream.write(msg.getBytes());
        }
        fileOutputStream.close();
        long time1 = System.nanoTime();
        return time1 - time;
    }

    public static void main(String[] args) throws IOException {
        File file = new File(MahoutConfig.class.getClassLoader().getResource("mahout").getPath() + "/dajitui.data");
        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println(file.getPath());
    }

}
