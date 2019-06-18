<?php

namespace app\manager\controller;

use think\View;
use think\Controller;

use PhpOffice\PhpSpreadsheet\Reader\Exception;
use PhpOffice\PhpSpreadsheet\Reader\Xlsx;
use think\Db;
use app\common\controller\Common;
use PhpOffice\PhpSpreadsheet\IOFactory;
use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Style\Alignment;
use think\Request;

class CredentialSearch extends Controller
{

    //车证查询界面的访问函数
    public function index(){
        //通过session获取用户名
        $user_name = session('user_name');

        //验证登录状态
        if (empty($user_name)) {
            echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        } else {
            //获取空界面
            $view = new View();

            //获取完整车证
            $view->data = db('car_license')->where("isvalid", 1)->select();

            //重定向
            return $view->fetch();
        }
    }

    //查询具体界面的访问函数
    public function trial($id){
        //通过session获取用户名
        $user_name = session('user_name');

        //验证登录状态
        if (empty($user_name)) {
            echo "您好，请登录<br/>";
            $this->redirect(url('login/index'));
        } else {
            //获取空界面
            $view = new View();

            //获取完整车证的具体信息
            $view->data = db('car_license')->where('id', $id)->find();

            //重定向
            return $view->fetch('more');
        }
    }

    // 导出数据库
    public function excelOutput(){
        $info = model("CredentialSearch")->getinfo();
        $spreadsheet = new Spreadsheet();
        $spreadsheet->setActiveSheetIndex(0)
            ->setCellValue('A1', '用户姓名')
            ->setCellValue('B1', '工号/学号')
            ->setCellValue('C1', '用户类型')
            ->setCellValue('D1', '所属部门')
            ->setCellValue('E1', '联系电话')
            ->setCellValue('F1', '车牌号码')
            ->setCellValue('G1', '车牌所有者')
            ->setCellValue('H1', '车证备注')
            ->setCellValue('I1', '申请时间')
            ->setCellValue('J1', '通过时间')
            ->setCellValue('K1', '有效期至')
            ->setCellValue('L1', '车证状态');
        $statusdict = array(0 => "无效", 1 => "有效", 2 => "挂失");
        $spreadsheet->getActiveSheet()->setTitle('固定车证信息');
        $i = 2; //从第二行开始
        foreach ($info as $data) {
            $spreadsheet->getActiveSheet()
                ->setCellValue('A' . $i, $data['usr_name'])
                ->setCellValue('B' . $i, $data['usr_number'])
                ->setCellValue('C' . $i, $data['type'])
                ->setCellValue('D' . $i, $data['department'])
                ->setCellValue('E' . $i, $data['usr_phone'])
                ->setCellValue('F' . $i, $data['car_number'])
                ->setCellValue('G' . $i, $data['car_owner'])
                ->setCellValue('H' . $i, $data['note'])
                ->setCellValue('I' . $i, $data['apply_date'])
                ->setCellValue('J' . $i, $data['pass_date'])
                ->setCellValue('K' . $i, $data['valid_date'])
                ->setCellValue('L' . $i, $statusdict[$data['status']]);

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
            ->setWidth(25);
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
            ->setWidth(15);

        $spreadsheet->getActiveSheet()->getStyle('A1:L' . $i)->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
        header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
        header('Content-Disposition: attachment;filename="固定车证信息.xlsx"');
        header('Cache-Control: max-age=0');
        $writer = IOFactory::createWriter($spreadsheet, 'Xlsx');
        $writer->save('php://output');
        exit;
    }

    // 导入数据库
    public function excelInput(){
        // 初始化reader类
        $reader = new Xlsx();
        try {
            // 检测是否有Excel文件
            $spreadsheet = $reader->load($_FILES['file']['tmp_name']);
        } catch (Exception $e) {
            $this->error('请上传Excel文件！');
        }
        $sheet = $spreadsheet->getActiveSheet();
        $sqlData = array();
        $count = 0;
        // 使用model
        $excelData = model("CredentialSearch");
        $statusdict = array("无效" => "0", "有效" => "1", "挂失" => "2");
        // 遍历Excel表格，将数据存入sqlData
        foreach ($sheet->getRowIterator(2) as $row) {
            $tmp = array();
            foreach ($row->getCellIterator() as $cell) {
                $tmp[] = $cell->getFormattedValue();
            }
            // 重复的不添加
            if ($excelData->findUserByWorkId($tmp[1]) == null) {
                $tmp = ['usr_name' => $tmp[0],
                    'usr_number' => $tmp[1],
                    'type' => $tmp[2],
                    'department' => $tmp[3],
                    'usr_phone' => $tmp[4],
                    'department' => $tmp[5],
                    'car_owner' => $tmp[6],
                    'note' => $tmp[7],
                    'apply_date' => $tmp[8],
                    'pass_date' => $tmp[9],
                    'valid_date' => $tmp[10],
                    'status' => $statusdict[trim($tmp[11])]
                ];
                // 本句有用，勿删
                if (trim($tmp['usr_number'])+"4396"!="4396")
                $sqlData[$count++] = $tmp;
            }
            else{
                continue;
            }
        }
        $addFlag = false ;
        //如果从Excel获取的数组为空，即用户提交的Excel表格与已有数据库全部重复
        if (empty($sqlData)){
            $this->success('添加成功,但Excel表格内容为空或与数据库内容相同，请检查');
        }
        else{
            $addFlag = $excelData->insertAllUser($sqlData);
        }
        //echo  $sqlData[0];
        if ($addFlag) {
            $this->success('添加成功,自动跳转');
        } else {
            $this->error('添加失败');
        }
    }
}