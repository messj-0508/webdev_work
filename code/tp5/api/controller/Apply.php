<?php
namespace app\api\controller;

use think\Controller;

class Apply extends Controller
{
  	public function test(){
    	$param = input('post.');
      	if($param['type'] == "teacher"){
          	$access = db("setting")->where("id",1)->find();
        } else if($param['type'] == "student"){
          	$access = db("setting")->where("id",2)->find();
        }  else {
          	$access = db("setting")->where("id",3)->find();
        } 
        if($access["f1_access"] == 0){
            $data = [
                'code' => 103,
                'result' => "",
            ];
            return json($data);		
        }
        $file = request()->file('image');
    
        // 移动到框架应用根目录/public/uploads/ 目录下
        if($file){
            $info = $file->move(ROOT_PATH . 'public' . DS . 'uploads');
        }
        $postdata = [
              'type'=> $param['type'],
              'usr_name' => $param['usr_name'],
              'usr_number' => $param['usr_number'],
              'department' => $param['major'],
              'usr_phone' => $param['usr_phone'],
              'car_number' => $param['car_number'],
              'car_owner' => $param['car_owner'],
              'note' => $param['note'],
        ];
        $model = model('app\api\model\Apply');
        $code = 200;
        if($param['step'] == 0){
            $ret = $model->selectForm($postdata);
            if(count($ret) == 0){
                $postdata['note'] = $param['note'];
                $postdata[$param['image_belong']] =  DS . 'uploads' .  DS . $info->getSaveName();
                $ret = $model->saveForm($postdata);
                if($ret != "success"){
                    $code = 101;
                }
            }else{
              $newdata[$param['image_belong']] = DS . 'uploads' .  DS . $info->getSaveName();
              $newdata['note'] = $param['note'];
              $newdata['status'] = 0;
              $ret = $model->updateForm($postdata, $newdata);
              if($ret != 1){
                  $code = 102;
              }
            }
        }else{
        	$postdata['status'] = 0;
            $newdata[$param['image_belong']] = DS . 'uploads' .  DS . $info->getSaveName();
            $newdata['status'] = $param['status'];
            $ret = $model->updateForm($postdata, $newdata);
            if($ret != 1){
                $code = 103;
            }
        }
        $data = [
        	'code' => $code,
            'result' => $ret,
        ];
        return json($data);
      
    }
  	/*
    ** code = 200 通过
    ** code = 101 插入消息失败
    ** code = 102 中间步骤更新消息失败
    ** code = 103 权限不足
    */
    public function save()
    {
        /*
        $postdata = [
            'usr_name' => '路人甲',
            'usr_number' => '1902230057',
            'department' => '软微测试部',
            'usr_phone' =>'153476482564',
            'car_number' => '京X XXXXXXX',
            'car_owner' => '路人甲',
			'car_card' => '行驶证图片存放路径',
			'usr_card' => '驾驶证图片存放路径',
			'note' => '测试API',
        ];
        */
    	$param = input('post.');
      	if($param['type'] == "teacher"){
          	$param['type'] = "教职工";
          	$access = db("setting")->where("id",1)->find();
        } else if($param['type'] == "student"){
          	$param['type'] = "学生";
          	$access = db("setting")->where("id",2)->find();
        }  else {
          	$param['type'] = "服务商";
          	$access = db("setting")->where("id",3)->find();
        } 
        if($access["f1_access"] == 0){
            $data = [
                'code' => 103,
                'result' => "",
            ];
            return json($data);		
        }
        $file = request()->file('image');
    	
        // 移动到框架应用根目录/public/uploads/ 目录下
        if($file){
            $info = $file->move(ROOT_PATH . 'public' . DS . 'uploads');
        }
        $postdata = [
              'type'=> $param['type'],
              'usr_name' => $param['usr_name'],
              'usr_number' => $param['usr_number'],
              'department' => $param['major'],
              'usr_phone' => $param['usr_phone'],
              'car_number' => $param['car_number'],
              'car_owner' => $param['car_owner'],
        ];
        $model = model('app\api\model\Apply');
        $code = 200;
        if($param['step'] == 0){
            $ret = $model->selectForm($postdata);
            if(count($ret) == 0){
                $postdata['note'] = $param['note'];
                $postdata[$param['image_belong']] = DS . 'uploads' .  DS . $info->getSaveName();
                $ret = $model->saveForm($postdata);
                if($ret != "success"){
                    $code = 101;
                }
            }else{
              $newdata[$param['image_belong']] = DS . 'uploads' .  DS . $info->getSaveName();
              $newdata['note'] = $param['note'];
              $newdata['status'] = 0;
              $ret = $model->updateForm($postdata, $newdata);
              if($ret != 1){
                  $code = 102;
              }
            }
        }else{
        	$postdata['status'] = 0;
            $newdata[$param['image_belong']] = DS . 'uploads' .  DS . $info->getSaveName();
            $newdata['status'] = $param['status'];
            $ret = $model->updateForm($postdata, $newdata);
            if($ret != 1){
                $code = 102;
            }
        }
        $data = [
        	'code' => $code,
            'result' => $ret,
        ];
        return json($data);
    }
    public function search(){
    	$param = input('post.');
        
        $model = model('app\api\model\Apply');
        $ret = $model->selectForm($param);
        $code = 200;
        $status = 0;
        $num = count($ret);
        if($num != 0){
            $status = 1;
        	for($i = 0; $i < $num; $i++){
                if($ret[$i]["status"] == 3){
                	$status = 3;
                }else if($ret[$i]["status"] == 2){
                	$status = 2;
                  	break;
                }
            }
        }
        $data = [
        	'code' => $code,
            'result' => $status,
        ];
        return json($data);
    }
}
