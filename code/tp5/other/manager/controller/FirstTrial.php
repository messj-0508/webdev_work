<?php
namespace app\manager\controller;

use think\View;
use think\Controller;
use think\Request;

class FirstTrial extends Controller
{
  
  	//初审列表界面的访问函数
    public function index()
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//获取空界面
            $view = new View();
          
          	//获取未审批的完整申请（即status为1）
          	$view->data = db('application_form')->where('status',1)->select();
          	
          	//重定向
        	return $view->fetch();
        }
    }
  
  	//初审具体界面的访问函数
    public function trial($id)
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//获取空界面
            $view = new View();
          
          	//获取申请的具体信息
            $view->data = db('application_form')->where('status',1)->where('id',$id)->find();
          	
          	//重定向
            return $view->fetch('trial');
        }
    }
  
  	//初审通过操作的响应函数
    public function pass($id)
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//数据库更新为初审通过状态（status为2）
            $res = db('application_form')->where('status',1)->where('id',$id)->update(['status' => 2]);
          
          	//根据反馈，返回更新结果
            if($res != 0){
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请初审通过操作成功",
                ];
                db("operation")->insert($oper_data);
              
              	//通知并重定向
                $this->success('修改成功', 'first_trial/index');
            }
            else{
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请初审通过操作失败",
                ];
                db("operation")->insert($oper_data);
              
              	//通知失败
                $this->error('修改失败');
            }
        }
    }
  
  	//初审拒绝操作的响应函数
    public function refuse($id)
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//数据库更新为初审拒绝状态（status为3）
            $res=db('application_form')->where('status',1)->where('id',$id)->update(['status' => 3]);
          
          	//根据反馈，返回更新结果
            if($res != 0){
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请初审拒绝操作成功",
                ];
                db("operation")->insert($oper_data);
              
              	//通知并重定向
                $this->success('修改成功', 'first_trial/index');
            }else{
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请初审拒绝操作失败",
                ];
                db("operation")->insert($oper_data);
              
              	//通知失败
                $this->error('修改失败');
            }
        }
    }
}