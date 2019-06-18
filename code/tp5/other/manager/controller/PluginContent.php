<?php
namespace app\manager\controller;

use think\View;
use think\Controller;
use think\Model;
use think\Request;
use think\Db;

class PluginContent extends Controller
{
    public function index()
    {
        //数据库连接、查询
        //如果没有则访问api并存储
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $view = new View();
          	$view->data = db('plugin_content')->select();
        	return $view->fetch();
        }
    }
      public function doModify()
    {
        $user_name = session('user_name');
        if(empty($user_name)){
          	echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        }else{
            $param = input('post.');
          	if(in_array("Page2",$param)){
            	$id = 2;
            }else if(in_array("Page1",$param)){
            	$id = 1;
            }else if(in_array("Page0",$param)){
            	$id = 0;
            }
            $post_form = [
                  'id'=>  $id,
            ];
            $postdata = [
                  'modified_time'=>date('Y-m-d H:i:s',time()),
                  'title' => $param['title'],
                  'content'=> $param['content'],
            ];
            $res = Db::name('plugin_content') -> where($post_form) -> update($postdata);
            if($res != 0){
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "修改须知操作成功",
                ];
                db("operation")->insert($oper_data);
                $this->success('修改成功', 'plugin_content/index');
            }else{
                $request = Request::instance();
                $oper_data = [
                  "ip" => $request->ip(),
                  "user" => $user_name,
                  "oper" => "修改须知操作失败",
                ];
                db("operation")->insert($oper_data);
                $this->error('修改失败');
            }
        }
    }
}