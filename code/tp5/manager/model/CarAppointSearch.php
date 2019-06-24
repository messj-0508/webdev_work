<?php

/*
 * 用于获取数据库的model
 * 即导出Excel的数据*/

namespace app\manager\model;

use think\Model;
use think\Db;


class CarAppointSearch extends Model{
    // 获取数据库信息
    public function getinfo(){
        $info = Db::table('car_appointment_form')
            ->field('usr_name,usr_number,type,department,usr_phone,driver_name,driver_phone,car_number,reason,appoint_data,period,note,apply_date')
            ->select();
        return $info;
    }

}