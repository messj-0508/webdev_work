//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    processData: [{
      name: '信息填写',//文字
      start: '#fff',//白色横线
      end: '#EFF3F6',//灰色横线
      icon: '../../res/img/process_2.png'//图标
    },
    {
      name: '提交审核',//文字
      start: '#EFF3F6',//灰色横线
      end: '#EFF3F6',//灰色横线
      icon: '../../res/img/process_3.png'//图标
    },
    {
      name: '已审核',//文字
      start: '#EFF3F6',//灰色横线
      end: '#fff',//白色横线
      icon: '../../res/img/process_1.png'//图标
    }],
    detailData: {
      progress: [{
        word: '填写完毕',
        state: 1
      },
      {
        word: '提交审核',
        state: 1
      },
      {
        word: '已审核',
        state: 0
      }]
    }
  },

  onLoad: function () {
    var that = this
    that.setPeocessIcon();
  },

  //进度条的状态
  setPeocessIcon: function () {
    var index = 0;//记录状态为1的最后的位置，记录当前点
    var processArr = this.data.processData
    // console.log("progress", this.data.detailData.progress)
    //console.log("progress2", this.data.processData.length)
    for (var i = 0; i < this.data.detailData.progress.length; i++) {
      var item = this.data.detailData.progress[i]
      //processArr[i].name = item.word//改名字
      if (item.state == 1) {
        index = i
        processArr[i].icon = "../../res/img/process_3.png"//已完成
        processArr[i].start = "#f7982a"//蓝色线
        processArr[i].end = "#f7982a"//蓝色线
      } else {
        processArr[i].icon = "../../res/img/process_1.png"//没开始
        processArr[i].start = "#EFF3F6"//灰色线
        processArr[i].end = "#EFF3F6"//灰色线
      }
    }
    processArr[index].icon = "../../res/img/process_2.png"
    processArr[index].end = "#EFF3F6"
    processArr[0].start = "#fff"
    processArr[this.data.detailData.progress.length - 1].end = "#fff"
    this.setData({
      processData: processArr
    })
  }
})
