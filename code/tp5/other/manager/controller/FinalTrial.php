<?php
namespace app\manager\controller;

use think\View;
use think\Controller;
use think\Request;

class FinalTrial extends Controller
{
  
  	//终审列表界面的访问函数
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
          
          	//获取初审成功的完整申请（即status为2）
          	$view->data = db('application_form')->where('status',2)->select();
          	
          	//重定向
        	return $view->fetch();
        }
    }
  
  	//终审具体界面的访问函数
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
          
          	//获取申请和须知的具体信息
            $plugin = db('plugin_content')->where('id',0)->find();
            $content = str_replace("<br/><br/>","<br/>",str_replace("\n","<br/>",str_replace("\r","<br/>",$plugin["content"])));
          	$view->plugin = [
            	"title" => $plugin["title"],
            	"content" => $content,
            ];
            $view->data = db('application_form')->where('status',2)->where('id',$id)->find();
          	
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
          	//数据库更新为终审通过状态（status为4）
            $res = db('application_form')->where('status',2)->where('id',$id)->update(['status' => 4]);
          
          	//根据反馈，返回更新结果
            if($res != 0){
              	
              	//申请数据具体信息获取
              	$data = db('application_form')->where('id',$id)->find();
              
              	//获取当前时间
                $t = strtotime('now');
              
              	//根据用户类型，确定车证有效年限
                if($data['type'] == "教师"||$data['type'] == "教职工"||$data['type'] == "teacher"){
                	$res = db('setting')->where('id',1)->find();
                }else if($data['type'] == "学生"||$data['type'] == "student"){
                	$res = db('setting')->where('id',2)->find();
                }else{
                	$res = db('setting')->where('id',3)->find();
            	}
          		$n = $res['valid_year'];
              
              	//向数据库插入车证
                $postdata = [
                      'type'=> $data['type'],
                      'usr_name' => $data['usr_name'],
                      'usr_number' => $data['usr_number'],
                      'department' => $data['department'],
                      'usr_phone' => $data['usr_phone'],
                      'car_number' => $data['car_number'],
                      'car_owner' => $data['car_owner'],
                      'car_card' => $data['car_card'],
                      'usr_card' => $data['usr_card'],
                      'other_img' => $data['other_img'],
                      'pass_date' => date('Y-m-d H:i:s',$t),
                      'valid_date' => date('Y-m-d H:i:s',$t+365*24*60*60*$n),
                  	
                ];
                db('car_license')->insert($postdata);
              
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请终审通过操作成功",
                ];
                db("operation")->insert($oper_data);
              
              	//通知并重定向
                $this->success('修改成功', 'final_trial/index');
            }
            else{
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请终审通过操作成功",
                ];
                db("operation")->insert($oper_data);
              
              	//通知失败
                $this->error('修改失败');
            }
        }
    }
  
  	//终审拒绝操作的响应函数
    public function refuse($id)
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//数据库更新为终审拒绝状态（status为5）
            $res=db('application_form')->where('status',2)->where('id',$id)->update(['status' => 5]);
          
          	//根据反馈，返回更新结果
            if($res != 0){
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请终审拒绝操作成功",
                ];
                db("operation")->insert($oper_data);
              
              	//通知并重定向
                $this->success('修改成功', 'final_trial/index');
            }else{
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => $id . "号申请终审拒绝操作失败",
                ];
                db("operation")->insert($oper_data);
              
              	//通知失败
                $this->error('修改失败');
            }
        }
    }
}