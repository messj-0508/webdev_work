<?php
namespace app\api\controller;

use think\Controller;

class Carappoint extends Controller
{
  	public function test(){
    	$param = input('post.');
        $postdata = [
            'type'=> $param['type'],
            'usr_name' => $param['usr_name'],
            'usr_number' => $param['usr_number'],
            'department' => $param['major'],
            'usr_phone' => $param['usr_phone'],
            'driver_name' => $param['driver_name'],
            'driver_phone' => $param['driver_phone'],
            'car_number' => $param['car_number'],
            'reason' => $param['reason'],
            'appoint_data' => $param['date'],
            'period' => $param['time'],
        ];
        $code = 200;
        $model = model('app\api\model\Carappoint');
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
    public function save()
    {
        /*
        $postdata = [
            'type' => 'student',
            'usr_name' => '学生甲',
            'usr_number' => '1902230057',
            'department' => '软微测试部',
            'usr_phone' =>'153476482564',
            'driver_name' => '亲友甲',
            'driver_phone' => '13214007664',
            'car_number' => '京X XXXXXXX',
            'reason' => '看望',
            'appoint_data' =>'2019-01-30',
            'period' => '下午时段',
			'note' => '测试API',
        ];
        */
    	$param = input('post.');
        $postdata = [
            'type'=> $param['type'],
            'usr_name' => $param['usr_name'],
            'usr_number' => $param['usr_number'],
            'department' => $param['major'],
            'usr_phone' => $param['usr_phone'],
            'driver_name' => $param['driver_name'],
            'driver_phone' => $param['driver_phone'],
            'car_number' => $param['car_number'],
            'reason' => $param['reason'],
            'appoint_data' => $param['date'],
            'period' => $param['time'],
        ];
        $code = 200;
        $model = model('app\api\model\Carappoint');
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
