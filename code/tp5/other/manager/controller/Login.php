<?php
namespace app\manager\controller;

use think\Controller;
use think\View;
use think\Validate;
use think\Request;

class Login extends Controller
{	
  	//登录界面的访问函数
    public function index()
    {
    	return $this->fetch();
    }   
  
  	//注册界面的访问函数
  	public function register()
    {    	
      	//通过session获取用户名
    	$user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//重定向到注册界面
            $view = new View();
            return $view->fetch('register');
        }
    }
  
  	//密码修改界面的访问函数
    public function change()
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
          	//重定向到密码修改界面
            $view = new View();
            return $view->fetch('change');
        }
    }
  
  	//设置修改界面的访问函数
  	public function setting()
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
          
          	//将获取的设置信息、专业部门信息绑定到界面
          	$view->data = db('setting')->select();
          	$view->major = db('major')->where("is_valid",1)->select();
          	$view->depart = db('department')->where("is_valid",1)->select();
          
          	//重定向到设置修改界面
            return $view->fetch('setting');
        }
    }
  
  	//退出登录的响应函数
    public function loginOut()
    {
      	//通过session获取用户名
      	$user_name = session('user_name');
    	
      	//记录操作
        $request = Request::instance();
        $oper_data = [
          "ip" => $request->ip(),
          "user" => $user_name,
          "oper" => "退出登录",
        ];
        db("operation")->insert($oper_data);
      
      	//消除session中存储的用户信息
    	session('user_id', null);
    	session('user_name', null);
      
        //重定向到登录界面
    	$this->redirect(url('login/index'));
    }
  
  	//登录操作的响应函数
    public function doLogin()
    {	
      	//接收post请求
    	$param = input('post.');
      	
      	//非空验证
    	if(empty($param['user_name'])){
    		$this->error('用户名不能为空');
    	}
    	if(empty($param['user_pwd'])){
    		$this->error('密码不能为空');
    	}
    	
    	//核实用户名
    	$has = db('users')->where('user_name', $param['user_name'])->find();
    	if(empty($has)){
    		$this->error('用户名密码错误');
    	}
      	
      	//核实密码，密码加密算法
      	$pd = md5($param['user_pwd']);
        $npd = $pd . "pku";
    	if($has['user_pwd'] != md5($npd)){
    		$this->error('用户名密码错误');
    	}
    	
    	//通过session记录用户登录信息
    	session('user_id', $has['id']); 
    	session('user_name', $has['user_name']);
      
      	//记录操作
    	$request = Request::instance();
      	$oper_data = [
          "ip" => $request->ip(),
          "user" => $has['user_name'],
          "oper" => "登录操作",
        ];
     	db("operation")->insert($oper_data);
      
      	//重定位到管理界面
    	$this->redirect(url('manage/index'));
    }
  
  	//密码修改的响应函数
    public function changePwd()
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
      		//接收post请求
            $param = input('post.');
          
          	//设置一个tp5的验证器，规则：非空、复杂、重复
            $rule = [
                'oldpwd'  =>  'require',
                'password'  =>  'require|min:9|confirm:password_confirm',
            ];
            $msg = [
                'oldpwd.require' => '旧密码不能为空',
                'password.require' => '新密码不能为空',
                'password.min' => '密码必须9位以上',
                'password.comfirm' => '两次密码不一致',
            ];
			$validate = new Validate($rule,$msg);
          
          	//将获取的数据进行验证，若错误则返回
          	$testdata = [
                'oldpwd' => $param['oldpwd'],
                'password' => $param['newpwd'],
                'password_confirm' => $param['comfirm'],
            ];
          	if(!$validate->check($testdata)){
                return json($testdata);
                return $this->error($validate->getError());
            }
          	
          	//根据用户名获取用户信息
    		$has = db('users')->where('user_name', $user_name)->find();
          	
          	//密码加密，并核实
            $pd = md5($param['oldpwd']);
            $npd = $pd . "pku";
          	if($has['user_pwd'] != md5($npd)){
                $this->error('密码错误');
            }
          	
          	//更新数据库中的密码
          	$data = [		
				'user_name' => $user_name,
			];
          	$newdata = [		
				'user_pwd' => md5($param['newpwd']),
			];
          	$res = db('users') -> where($data) -> update($newdata);
          
          	//根据反馈，返回更新结果
            if($res != 0){
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "修改密码成功",
                ];
                db("operation")->insert($oper_data);
              	
              	//清空session中的用户信息并退出
                session('user_id', null);
                session('user_name', null);
                $this->success('修改密码成功', 'login/index');
            }else{
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "修改密码失败",
                ];
                db("operation")->insert($oper_data);
              
              	//通知失败
                $this->error('修改密码失败');
            }
        }
    }
  	
  	//设置修改操作的响应函数
  	public function changeSet()
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
      		//接收post请求
            $param = input('post.');
          
            //初始化三种修改的返回值：用户类型权限设置、专业\部门表增与删
        	$res1 = 0;
        	$res2 = 0;
          	$res3 = 0;
          
          	//根据page的编号、来确定接受的信息所属（即id值）
          	if(in_array("Page2",$param)){
              	
              	//设置表（服务商）数据绑定
            	$id = 3;
                $postdata = [
                      'f1_access'=> (int)$param['f7_access'],
                      'f2_access'=> (int)$param['f8_access'],
                      'f3_access'=> (int)$param['f9_access'],
                      'valid_year'=> (int)$param['year'],
                ];
            }else if(in_array("Page1",$param)){
              
              	//专业表删除操作
              	if($param["deleteid"] != ""){
              		$data = db('major') -> where("id",(int)$param["deleteid"]) -> find();
              		$res1 = db('major') -> where("id",(int)$param["deleteid"]) -> update(["is_valid" => 0,"major"=>$data["id"].$data["major"],]);
                }
              
              	//专业表添加操作
              	if($param["addmajor"] != ""){
                  	$rs =   db('major') -> where(["major" => $param["addmajor"]]) ->select();
                    if(count($rs)!=0){
              			$res2 = db('major') -> where(["major" => $param["addmajor"],"is_valid" => 0,]) ->update(["is_valid" => 1,]);
                    }else{
                    	$res2 = db('major') -> insert(["major" => $param["addmajor"],]);
                    }
                }
              	
              	//设置表（学生）数据绑定
            	$id = 2;
                $postdata = [
                      'f1_access'=> (int)$param['f4_access'],
                      'f2_access'=> (int)$param['f5_access'],
                      'f3_access'=> (int)$param['f6_access'],
                      'valid_year'=> (int)$param['year'],
                ];
            }elseif(in_array("Page0",$param)){
              
              	//部门表删除操作
              	if($param["deleteid"] != ""){
              		$data = db('department') -> where("id",(int)$param["deleteid"]) -> find();
              		$res1 = db('department') -> where("id",(int)$param["deleteid"]) -> update(["is_valid" => 0,"department"=>$data["id"].$data["department"],]);
                }
              
              	//部门表添加操作
              	if($param["addmajor"] != ""){
                  	$rs =   db('department') -> where(["department" => $param["addmajor"]]) ->select();
                    if(count($rs)!=0){
              			$res2 = db('department') -> where(["department" => $param["addmajor"],]) ->update(["is_valid" => 1,]);
                    }else {
                    	$res2 = db('department') -> insert(["department" => $param["addmajor"],]);
                    }
                }
              	
              	//设置表（教职工）数据绑定
            	$id = 1;
                $postdata = [
                      'f1_access'=> (int)$param['f1_access'],
                      'f2_access'=> (int)$param['f2_access'],
                      'f3_access'=> (int)$param['f3_access'],
                      'valid_year'=> (int)$param['year'],
                ];
            }
              	
            //设置表更新操作
            $post_form = [
                  'id'=>  $id,
            ];
            $res3 = db('setting') -> where($post_form) -> update($postdata);
          
          	//根据反馈，返回更新结果
            $res = $res1+$res2+$res3;
            if($res != 0){
              	//记录操作
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "修改设置",
                ];
                db("operation")->insert($oper_data);
              	
              	//重定向设置页面
                $this->success('成功修改'.$res."条数据", 'login/setting');
            }else
                $this->error('未发生任何设置修改');
        }
    }
  	
  	//添加新管理员的响应函数
  	public function addUser()
    {
      	//通过session获取用户名
        $user_name = session('user_name');
      
      	//验证登录状态
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
      		//接收post请求
            $param = input('post.');
          
          	//设置一个tp5的验证器，规则：非空、复杂、重复
            $rule = [
              'user'  =>  'require',
              'password'  =>  'require|min:9|confirm:password_confirm',
            ];
            $msg = [
              'user.require' => '用户名不能为空',
              'password.require' => '密码不能为空',
              'password.min' => '密码必须6位以上',
              'password.confirm' => '两次密码不一致',
            ];
            $validate = new Validate($rule,$msg);
          
          	//将获取的数据进行验证，若错误则返回
            $testdata = [
                'user' => $param['user_name'],
                'password' => $param['password'],
                'password_confirm' => $param['comfirm'],
            ];
            if(!$validate->check($testdata)){
                return $this->error($validate->getError());
            }
          	
          	//用户名唯一性验证
            $res = db("users")->where('user_name', $param['user_name'])->count();
            if($res != 0)
              return $this->error('注册失败,该用户已被注册');
          
          	//向数据库新管理员信息
            $pd = md5($param['password']);
            $npd = $pd . "pku";
            $data = [		//接受传递的参数
                'user_real_name' => $param['user_real_name'],
                'user_name' => $param['user_name'],
                'user_pwd' => md5($npd),
            ];

            /*	Db('表名') 数据库助手函数*/
            if(Db('users') -> insert($data)){
              
              //记录操作
              $request = Request::instance();
              $oper_data = [
                "ip" => $request->ip(),
                "user" => $user_name,
                "oper" => "添加新用户成功",
              ];
              db("operation")->insert($oper_data);
              
              //通知并重定向
              return $this->success('注册成功', 'manage/index');	
            }else{
              
              //记录操作
              $request = Request::instance();
              $oper_data = [
                "ip" => $request->ip(),
                "user" => $user_name,
                "oper" => "添加新用户失败",
              ];
              db("operation")->insert($oper_data);
              
              //通知失败
              return $this->error('注册失败');
            }
        }
    }
}