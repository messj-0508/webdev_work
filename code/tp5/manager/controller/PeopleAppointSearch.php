<?php
namespace app\manager\controller;

use think\View;
use think\Controller;

use think\Db;
use PhpOffice\PhpSpreadsheet\Reader\Exception;
use PhpOffice\PhpSpreadsheet\Reader\Xlsx;
use app\common\controller\Common;
use PhpOffice\PhpSpreadsheet\IOFactory;
use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Style\Alignment;
use think\Request;

class PeopleAppointSearch extends Controller
{
  
  	//人员预约查询界面的访问函数
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
          
          	//获取人员预约信息
          	$view->data = db('people_appointment_form')->select();
          	
          	//重定向
        	return $view->fetch();
        }
    }
  
  	//查询具体界面的访问函数
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
          
          	//获取人员预约的具体信息
            $view->data = db('people_appointment_form')->where('id',$id)->find();
          	
          	//重定向
            return $view->fetch('more');
        }
    }

    // 导出数据库
    public function excelOutput(){
        $info = model("PeopleAppointSearch")->getinfo();
        $spreadsheet = new Spreadsheet();
        $spreadsheet->setActiveSheetIndex(0)
            ->setCellValue('A1', '用户姓名')
            ->setCellValue('B1', '工号/学号')
            ->setCellValue('C1', '用户类型')
            ->setCellValue('D1', '所属部门')
            ->setCellValue('E1', '联系电话')
            ->setCellValue('F1', '入校人员姓名')
            ->setCellValue('G1', '入校人员电话')
            ->setCellValue('H1', '入校人数')
            ->setCellValue('I1', '入校原因')
            ->setCellValue('J1', '预约入校日期')
            ->setCellValue('K1', '预约入校时段')
            ->setCellValue('L1', '备注')
            ->setCellValue('M1', '申请时间');
        $spreadsheet->getActiveSheet()->setTitle('临时人员入校信息');
        $i = 2; //从第二行开始
        foreach ($info as $data) {
            $spreadsheet->getActiveSheet()
                ->setCellValue('A' . $i, $data['usr_name'])
                ->setCellValue('B' . $i, $data['usr_number'])
                ->setCellValue('C' . $i, $data['type'])
                ->setCellValue('D' . $i, $data['department'])
                ->setCellValue('E' . $i, $data['usr_phone'])
                ->setCellValue('F' . $i, $data['people_name'])
                ->setCellValue('G' . $i, $data['people_phone'])
                ->setCellValue('H' . $i, $data['people_number'])
                ->setCellValue('I' . $i, $data['reason'])
                ->setCellValue('J' . $i, $data['appoint_data'])
                ->setCellValue('K' . $i, $data['period'])
                ->setCellValue('L' . $i, $data['note'])
                ->setCellValue('M' . $i, $data['apply_date']);
            $i++;
        }
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('A')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('B')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('C')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('D')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('E')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('F')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('G')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('H')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('I')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('J')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('K')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('L')
            ->setWidth(20);
        $spreadsheet->getActiveSheet()
            ->getColumnDimension('M')
            ->setWidth(20);

        $spreadsheet->getActiveSheet()->getStyle('A1:M' . $i)->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
        header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
        header('Content-Disposition: attachment;filename="临时人员入校信息.xlsx"');
        header('Cache-Control: max-age=0');
        $writer = IOFactory::createWriter($spreadsheet, 'Xlsx');
        $writer->save('php://output');
        exit;
    }
}