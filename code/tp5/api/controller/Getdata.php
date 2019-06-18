<?php 
namespace app\api\controller;

use think\Controller;
use think\Db;

class Getdata extends Controller
{
  	//从数据库获取固定车证办理的插件数据
    public function all()
    {
    	$param = input('post.');
        $major = db("major")->where("is_valid",1)->select();
      	$fmajor = array("请选择");
        for($i=0; $i<count($major); $i++){
          	$fmajor[$i+1] = $major[$i]["major"];
        }
        $depart = db("department")->where("is_valid",1)->select();
      	$fdepart = array("请选择");
        for($i=0; $i<count($depart); $i++){
          	$fdepart[$i+1] = $depart[$i]["department"];
        }
        $plugin = db("plugin_content")->where("isvalid",1)->select();
      	$fplugin = array();
        for($i=0; $i<count($plugin); $i++){
          	$fplugin[$i] = ["title" => $plugin[$i]["title"],"content" => $plugin[$i]["content"],];
        }
        $set = db("setting")->select();
      	$fset = array();
        for($i=0; $i<count($set); $i++){
          	$fset[$i] = ["f1" => $set[$i]["f1_access"],"f2" => $set[$i]["f2_access"],"f3" => $set[$i]["f3_access"],];
        }
        $data = [
            'major' => $fmajor,
            'depart' => $fdepart,
            'plugin' => $fplugin,
          	'fset' => $fset,
        ];
        return json($data);
    }
    public function mycard()
    {
    	$param = input('post.');
        if($param!=[]&&($param["usr_name"]&&$param["usr_number"])){
            $license = db("car_license")->where($param)->where("isvalid",1)->where("status",">",0)->order('apply_date','desc')->limit(20)->select();
            $flicense = array();
            for($i=0; $i<count($license); $i++){
                $flicense[$i] = [
                    'car_number' => $license[$i]["car_number"],
                    'valid_date' => substr($license[$i]["valid_date"], 0, 10),
                    'status' => $license[$i]["status"],
                ];
            }
            $data = [
                'license' => $flicense,
            ];
        }else{
            $data = [];
        }
        return json($data);
    }
    public function myapply()
    {
    	$param = input('post.');
        if($param!=[]&&($param["usr_name"]&&$param["usr_number"])){
            $license = db("application_form")->where($param)->where("status",">",0)->order('apply_date','desc')->limit(30)->select();
            $flicense = array();
            for($i=0; $i<count($license); $i++){
                $flicense[$i] = [
                    'car_number' => $license[$i]["car_number"],
                    'apply_date' => substr($license[$i]["apply_date"], 0, 10),
                    'status' => $license[$i]["status"],
                ];
            }
            $data = [
                'applyform' => $flicense,
            ];
        }else{
            $data = [];
        }
        return json($data);
    }
    public function mytempcar()
    {
    	$param = input('post.');
        if($param!=[]&&($param["usr_name"]&&$param["usr_number"])){
            $license = db("car_appointment_form")->where($param)->order('apply_date','desc')->limit(30)->select();
            $flicense = array();
            for($i=0; $i<count($license); $i++){
                $flicense[$i] = [
                    'car_number' => $license[$i]["car_number"],
                    'appoint_data' => $license[$i]["appoint_data"],
                    'period' => $license[$i]["period"],
                ];
            }
            $data = [
                'appointion' => $flicense,
            ];
        }else{
            $data = [];
        }
        return json($data);
    }
    public function mytempmen()
    {
    	$param = input('post.');
        if($param!=[]&&($param["usr_name"]&&$param["usr_number"])){
            $license = db("people_appointment_form")->where($param)->order('apply_date','desc')->limit(30)->select();
            $flicense = array();
            for($i=0; $i<count($license); $i++){
                $flicense[$i] = [
                    'car_number' => $license[$i]["people_name"],
                    'appoint_data' => $license[$i]["appoint_data"],
                    'period' => $license[$i]["period"],
                ];
            }
            $data = [
                'appointion' => $flicense,
            ];
        }else{
            $data = [];
        }
        return json($data);
    }
}