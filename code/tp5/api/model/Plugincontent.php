<?php 
namespace app\api\model;

use think\Model;
use think\Db;

class Plugincontent extends Model
{
    public function selectForm($data)
    {
        $result = Db::name('plugin_content') -> where($data) -> select();
        return $result;
    }
  
    public function findForm0($data)
    {
    $result = Db::name('plugin_content') -> where($data) -> find();
    return $result;
    }
  
}
