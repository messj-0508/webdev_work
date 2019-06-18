<?php 

/*
  这次使用的方法是图片和表单数据分开存
  图片和表单数据同时从前端出发插入数据库
  表单先插入数据库，等待0.5秒后插入图片数据
  这里存的是图片数据
*/
namespace app\api\controller;

use think\Controller;
use think\Db;
use think\Request;
use think\Model;

class Addpic extends Controller{
  	//从数据库获取固定车证办理的插件数据
    public function save(){
        // 跨域申请需要显示取得许可
        header('Access-Control-Allow-Origin:*');  
        // 响应类型  
        header('Access-Control-Allow-Methods:*');  
        // 响应头设置  
        header('Access-Control-Allow-Headers:x-requested-with,content-type');  
      
        $file1 = request()->file("image1");
        $file2 = request()->file("image2");
        $file3 = request()->file("image3");
      	$model = model('app\api\model\Addpic');
        $postdata =[
              'isvalid'=>1,
            ];
        // 移动到框架应用根目录/public/uploads/ 目录下
        // 如果管理员上传了行驶证照片
        if($file1){
          $info1 = $file1->move(ROOT_PATH . 'public' . DS . 'uploads');
          $postdata['car_card'] =  DS . 'uploads' .  DS . $info1->getSaveName();
        }
      
        // 如果管理员上传了驾驶证照片
        if($file2){
          $info2 = $file2->move(ROOT_PATH . 'public' . DS . 'uploads');
          $postdata['usr_card'] =  DS . 'uploads' .  DS . $info2->getSaveName();
        }
      
      	// 如果管理员上传了附加材料照片
        if($file3){
          $info3 = $file3->move(ROOT_PATH . 'public' . DS . 'uploads');
          $postdata['other_img'] =  DS . 'uploads' .  DS . $info3->getSaveName();
        }
        // 让图片找到一个isvalid=0的地方插入，不安全
        $find_pic_to_add =[
          'isvalid'=>0,
        ];
      //等待1秒，让表单的文字先插入，然后更新刚才插入的图片，有时间应该修改这个办法，不安全
        sleep(0.5);
        $ret2 = $model->updateForm($find_pic_to_add,$postdata);
        if($res2 != 0)
          return json("ok");
        else
          return json("failed");
    }
}
