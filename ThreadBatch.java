package Thread;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
/*
* 例如发送给10（11）个用户，设置每个线程最多跑2个用户
* */
class UserList extends Thread{
    private  List<UserEntity> userList;

    public UserList(List<UserEntity> list){
       this.userList=list;
   }


    @Override
    public void run() {
        for (UserEntity userEntity:userList) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
         //   System.out.println("userId:" +getId()+" userName:" +getName());
            System.out.println("Usrname:"+getName() +userEntity.toString());
            //接下来写与第三方对接的接口
        }

    }
}
public class ThreadBatch {
    public static void main(String[] args) {
        //初始化用户
        List<UserEntity> initUser = ThreadBatch.initUser();
        //设置每个线程最多跑多少数据
        int userCount=2;
        //计算每个线程数，并且计算每个线程跑哪些数据
        List<List<UserEntity>> lists = ListUtils.splitList(initUser, userCount);
        for (int i = 0; i < lists.size(); i++) {
            List<UserEntity> userEntities = lists.get(i);
           // System.out.println("int i"+i+userEntities.toString());
            //让子线程进行分批异步处理
            UserList userList = new UserList(userEntities);
            userList.start();
        }

    }
    public static List<UserEntity> initUser(){
        List<UserEntity> arrayList = new ArrayList<UserEntity>();
        for (int i = 0; i <11 ; i++) {
            arrayList.add(new UserEntity("userId:"+i,"userName:"+i));
        }
        return arrayList;
    }
}
