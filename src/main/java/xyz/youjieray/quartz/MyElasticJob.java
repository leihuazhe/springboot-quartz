package xyz.youjieray.quartz;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * elastic-job
 *
 * @author leihz
 * @date 2017/7/5 10:32
 */
//todo prepate to do
public class MyElasticJob implements SimpleJob{

    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
