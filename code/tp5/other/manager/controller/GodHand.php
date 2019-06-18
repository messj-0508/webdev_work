<?php
namespace app\manager\controller;

use think\View;
use think\Controller;
use think\Model;
use think\Request;
use think\Db;

class GodHand extends Controller
{
    public function add()
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
        	return $view->fetch('add');
        }
    }
  	
  	//处理管理员添加车证的函数
  
  	public function doAdd(){
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
      
            $param = input('post.');
      		//左边是wjj，右边是wwr
      		$post_data_add = [
              'usr_name'=>$param['user_name'],
              'type'=>$param['sex1'],
              'usr_number'=>$param['user_num'],
              'usr_phone'=>$param['usr_phone'],
              'car_number'=>$param['car_number'],
              'department'=>$param['user_department'],
              'pass_date'=>date('Y-m-d H:i:s',time()),
              'valid_date'=>date('Y-m-d H:i:s', strtotime("+10 year")),
              'car_owner'=>$param['owner'],
              'note'=>$param['note'],
              'isvalid'=>0,
            ];
      		//采用先传图片，再更新的方法，可能有隐患
      		$find_pic_to_add =[
              'usr_name'=>"",
              'isvalid'=>0,
            ];
            $res = Db::name('car_license') -> insert($post_data_add);
      		if($res != 0){
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "快速添加操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->success('添加成功', 'manage/index');
            }else{
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "快速添加操作失败",
                ];
                db("operation")->insert($oper_data);
                $this->error('操作失败');
            }
          	//return json($param);
        }
    } 
      
    public function loss()
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
          	$view->data = db('car_license')->where("isvalid", 1)->where("status", 1)->select();
            return $view->fetch('loss');
        }
    }
      
    public function recover()
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
          	$view->data = db('car_license')->where("isvalid", 1)->where("status", 2)->select();
        	return $view->fetch('recover');
        }
    }
  
    public function invalid()
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
          	$view->data = db('car_license')->where("isvalid", 1)->where("status", 1)->select();
        	return $view->fetch('invalid');
        }
    }
  
    public function carappoint()
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
        	return $view->fetch('carappoint');
        }
    }
  
  	public function docarappoint(){
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $param = input('post.');
          	$date_now = date('Y-m-d H:i:s',time());
      		$post_data_carappoint = [
              'usr_name'=>"管理员添加",
              'type'=>"------",
              'usr_number'=>"------",
              'department'=>"------",
              'usr_phone'=>"------",
              'period'=>"全天",
              'driver_name'=>$param['driver_name'],
              'car_number'=>$param['car_number'],
              'appoint_data'=>$param['appoint_data'],
              'driver_phone'=>$param['driver_phone'],
              'reason'=>$param['reason'],
              'note'=>$param['note'],
              'apply_date'=>$date_now,
            ];
            $res = Db::name('car_appointment_form') -> insert($post_data_carappoint);
      		if($res != 0){
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "车辆预约操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->success('预约成功', 'manage/index');
            }else{
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "车辆预约操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->error('操作失败');
            }
        }
    }
  
    public function peopleappoint()
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
        	return $view->fetch('peopleappoint');
        }
    }
  
    public function dopeopleappoint(){
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $param = input('post.');
          	$date_now = date('Y-m-d H:i:s',time());
      		$post_data_peopleappoint = [
              'usr_name'=>"管理员添加",
              'type'=>"------",
              'usr_number'=>"------",
              'department'=>"------",
              'usr_phone'=>"------",
              'period'=>"全天",
              'people_name'=>$param['people_name'],
              'people_number'=>$param['people_number'],
              'appoint_data'=>$param['appoint_data'],
              'people_phone'=>$param['people_phone'],
              'reason'=>$param['reason'],
              'note'=>$param['note'],
              'apply_date'=>$date_now,
            ];
            $res = Db::name('people_appointment_form') -> insert($post_data_peopleappoint);
      		if($res != 0){
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "人员预约操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->success('预约成功', 'manage/index');
            }else{
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "人员预约操作失败",
                ];
                db("operation")->insert($oper_data);
                $this->error('操作失败');
            }
        }
    }
  
    public function lost($id)
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
            $view->data = db('car_license')->where('id',$id)->find();
            return $view->fetch('lost');
        }
    }    
  
    public function losted($id)
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	$res=db('car_license')->where('id',$id)->update(['status' => 2]);
            if($res != 0){
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号车证挂失操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->success('挂失成功', 'god_hand/loss');
            }else{
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号车证失效操作失败",
                ];
                db("operation")->insert($oper_data);
                $this->error('操作失败');
            }
        }
    } 
  
	public function recoverize($id)
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
            $view->data = db('car_license')->where('id',$id)->find();
            return $view->fetch('recoverize');
        }
    }
  
	public function recoverized($id)
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	$res=db('car_license')->where('id',$id)->update(['status' => 1]);
            if($res != 0){
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号车证恢复操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->success('该车证已成功恢复', 'god_hand/recover');
            }else{
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号车证恢复操作失败",
                ];
                db("operation")->insert($oper_data);
                $this->error('操作失败');
            }
        }
    }
  
	public function invalidize($id)
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
            $view->data = db('car_license')->where('id',$id)->find();
            return $view->fetch('invalidize');
        }
    }
  
	public function invalidized($id)
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	$res=db('car_license')->where('id',$id)->update(['status' => 0]);
            if($res != 0){
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号车证失效操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->success('该车证已失效', 'god_hand/invalid');
            }else{
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号车证失效操作失败",
                ];
                db("operation")->insert($oper_data);
                $this->error('操作失败');
            }
        }
    }
}