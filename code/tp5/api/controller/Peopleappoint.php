<?php
namespace app\api\controller;

use think\Controller;

class Peopleappoint extends Controller
{
    public function save()
    {
        /*
        $postdata = [
            'type' => 'student',
            'usr_name' => '学生甲',
            'usr_number' => '1902230057',
            'department' => '软微测试部',
            'usr_phone' =>'153476482564',
            'people_name' => '亲友甲',
            'people_phone' => '13214007664',
            'people_number' => '京X XXXXXXX',
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
            'people_name' => $param['people_name'],
            'people_phone' => $param['people_phone'],
            'people_number' => $param['people_number'],
            'reason' => $param['reason'],
            'appoint_data' => $param['date'],
            'period' => $param['time'],
        ];
        $code = 200;
        $model = model('app\api\model\Peopleappoint');
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
            'result' => $postdata,
        ];
        return json($data);
    }
}
