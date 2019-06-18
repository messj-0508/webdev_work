<?php 
namespace app\api\controller;

use think\Controller;
use think\Db;

class Plugincontent extends Controller
{
  	//从数据库获取固定车证办理的插件数据
    public function search()
    {
    	$param = input('post.');
        $postdata = [
            'id'=> 0,
        ];
        $code = 200;
        $model = model('app\api\model\Plugincontent');
        $ret = $model->selectForm($postdata);
        if(count($ret) == 0){
			$postdata['note'] = $param['note'];
            $ret = $model->saveForm($postdata);
            if($ret != "success"){
                $code = 101;
            }
        }else{
          	$code = 102;
        }
        $data = [
        	'code' => $code,
            'result' => $ret,
        ];
        return json($data);
    }
  //从数据库获取临时车辆办理的插件数据
      public function search1()
    {
    	$param = input('post.');
        $postdata = [
            'id'=> 1,
        ];
        $code = 200;
        $model = model('app\api\model\Plugincontent');
        $ret = $model->selectForm($postdata);
        if(count($ret) == 0){
			$postdata['note'] = $param['note'];
            $ret = $model->saveForm($postdata);
            if($ret != "success"){
                $code = 101;
            }
        }else{
          	$code = 102;
        }
        $data = [
        	'code' => $code,
            'result' => $ret,
        ];
        return json($data);
    }
  //从数据库获取临时人员办理的插件数据
      public function search2()
    {
    	$param = input('post.');
        $postdata = [
            'id'=> 2,
        ];
        $code = 200;
        $model = model('app\api\model\Plugincontent');
        $ret = $model->selectForm($postdata);
        if(count($ret) == 0){
			$postdata['note'] = $param['note'];
            $ret = $model->saveForm($postdata);
            if($ret != "success"){
                $code = 101;
            }
        }else{
          	$code = 102;
        }
        $data = [
        	'code' => $code,
            'result' => $ret,
        ];
        return json($data);
    }
}
