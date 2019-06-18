<?php 
namespace app\api\model;

use think\Model;
use think\Db;

class Addpic extends Model
{
    public function saveForm($application_info)
    {
        if(Db::name('car_license') -> insert($application_info)){
          return 'success';
        }else{
          return 'fail';
        }
    }
  
    public function selectForm($application_info)
    {
        $result = Db::name('car_license') -> where($application_info) -> select();
        return $result;
    }
  
    public function findForm($application_info)
    {
        $result = Db::name('car_license') -> where($application_info) -> find();
        return $result;
    }
  
    public function updateForm($application_info, $new_info)
    {
        $result = Db::name('car_license') -> where($application_info) -> update($new_info);
        return $result;
    }
}
