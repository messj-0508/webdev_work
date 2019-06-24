<?php
namespace app\manager\controller;

use think\Controller;
use think\View;

class Manage extends Controller
{
  	//管理首页界面的访问函数
    public function index()
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//统计信息
          	$res1 = db("application_form")->where("status",1)->count();
          	$res2 = db("application_form")->where("status",2)->whereor('status', 3)->count();
          	$res3 = db("application_form")->where("status",4)->whereor('status', 5)->count();
          	$res4 = db("car_license")->where("isvalid",1)->where('status', 1)->count();
          	$set = db("setting")->select();
          
          	//获取空界面
            $view = new View();
          
          	//数据绑定
          	$view->data = [
            	"r1" => $res1,
            	"r2" => $res2,
            	"r3" => $res3,
            	"r4" => $res4,
            ];
          	$view->set = $set;
          
          	//重定向
        	return $view->fetch();
        }
    }
}