sendWarningToTop = function (msg, duration, callback) {
  var content = '<div class="dialog-warning-top">' + msg + '</div>';

  $('body').append(content);

  var $tipBox = $('.dialog-warning-top'),
      width = $tipBox.width();

  $tipBox.css({
    'margin-left': -(width / 2),
    'margin-top': 20,
    'opacity': 0
  });

  $tipBox.animate({
    'opacity': 1,
    'margin-top': 0
  }, 200, function() {
    // 自动隐藏
    window.cc_timerSendWarningToTop = setTimeout(function() {
      $tipBox.fadeOut(function() {
        $tipBox.remove();
        typeof callback === 'function' && callback();
      });
    }, duration || 3000);
  });
  clearTimeout(window.cc_timerSendWarningToTop);
};

sendSuccessToTop = function(msg, duration, callback) {
  var content = '<div class="dialog-success-top">' + msg + '</div>';

  $('body').append(content);

  var $tipBox = $('.dialog-success-top'),
      width = $tipBox.width();

  $tipBox.css({
    'margin-left': -(width / 2),
    'margin-top': 20,
    'opacity': 0
  });

  $tipBox.animate({
    'opacity': 1,
    'margin-top': 0
  }, 400, function() {
    // 自动隐藏
    window.cc_timerSendSuccessToTop = setTimeout(function() {
      $tipBox.fadeOut(function() {
        $tipBox.remove();
        typeof callback === 'function' && callback();
      })
    }, duration || 3000);
  });
  clearTimeout(window.cc_timerSendSuccessToTop);
};

$('#searchcommit').on("click",function () {
  if ($('#id').val() == ''){
    sendWarningToTop('学号不能为空',2000,function () {
      console.log("学号空");
    })
  }else {
    let id = $('#id').val();
    let $value = $('#value');
    let $absent = $('#absent');
    $("#commit").attr({"disabled":'disabled'});
    setTimeout(function(){ $("#commit").removeAttr("disabled"); }, 1500);
    $.get('/search/'+id,function (data) {
          //判断是不是对象数组
          console.log(data)
          if (data == 'false'){
            sendWarningToTop("未找到该学号学生",5000,function () {
              console.log("未找到");
            })
          }else if (data.studentID != undefined){
            /**
             * 根据id查找必定一个人，除非有多条缺课记录
             */

            /**
             * 当返回结果为一个人且有缺课记录时
             */
            if (data.absentType != undefined){
              $('.title').remove()
              $value.empty();
              let title = "";
              title += "<th class='title'>"+"逃课日期"+"</th>";
              title += "<th class='title'>"+"第几节课"+"</th>";
              title += "<th class='title'>"+"课程名称"+"</th>";
              title += "<th class='title'>"+"逃课类型"+"</th>";
              title += "<th class='title operation'>"+"操作"+"</th>";
              $absent.append(title);
              let result = "";
              result += "<tr class='stu"+data.studentID+"'>";
              result += "<td>" + data.studentID + "</td>";
              result += "<td>" + data.name + "</td>";
              result += "<td>" + data.sex + "</td>";
              result += "<td>" + data.age + "</td>";
              result += "<td>" + data.classNum + "</td>";
              result += "<td>" + data.period + "</td>";
              result += "<td>" + data.checkData + "</td>";
              result += "<td>" + data.courseName + "</td>";
              result += "<td>" + data.absentType + "</td>";
              result += "<td><a onclick='deleteAbsent(this)' name='"+data.studentID+"' class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
                  "<form method='post' class='hideForm"+data.studentID+"' hidden><input name='beforeID'  class='input"+data.studentID+"'  hidden/></form><button  id='"+data.ID+"' class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterAbsent(this)' name='"+data.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
              result += "</tr>";
              $value.append(result);
            }else {
              $('.title').remove();
              $value.empty();
              $absent.append("<th class='title operation'>"+"操作"+"</th>");
              let result = "";
              result += "<tr class='stu"+data.studentID+"'>";
              result += "<td>" + data.studentID + "</td>";
              result += "<td>" + data.name + "</td>";
              result += "<td>" + data.sex + "</td>";
              result += "<td>" + data.age + "</td>";
              result += "<td>" + data.classNum + "</td>";
              result += "<td><a onclick='deleteID(this)' name='"+data.studentID+"' class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue delete' style='margin-right:10%;text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
                  "<form method='post' class='hideForm"+data.studentID+"' hidden><input name='beforeID'  class='input"+data.studentID+"'  hidden/></form><button  class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterStudent(this)' name='"+data.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>"
              result += "</tr>";
              $value.append(result);
            }
          }else {
            /**
             * 返回结果不是一个人的时候
             */
            $('.title').remove();
            $value.empty();
            let title = "";
            title += "<th class='title'>"+"逃课日期"+"</th>";
            title += "<th class='title'>"+"第几节课"+"</th>";
            title += "<th class='title'>"+"课程名称"+"</th>";
            title += "<th class='title'>"+"逃课类型"+"</th>";
            title += "<th class='title operation'>"+"操作"+"</th>";
            $absent.append(title);
            for (let obj of data){
              let result = "";
              result += "<tr class='stu"+obj.studentID+"'>";
              result += "<td>" + obj.studentID + "</td>";
              result += "<td>" + obj.name + "</td>";
              result += "<td>" + obj.sex + "</td>";
              result += "<td>" + obj.age + "</td>";
              result += "<td>" + obj.classNum + "</td>";
              result += "<td>" + obj.period + "</td>";
              result += "<td>" + obj.checkData + "</td>";
              result += "<td>" + obj.courseName + "</td>";
              result += "<td>" + obj.absentType + "</td>";
              result += "<td><a onclick='deleteAbsent(this)' name='"+obj.studentID+"' class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
                  "<form method='post' class='hideForm"+obj.studentID+"' hidden><input name='beforeID'  class='input"+obj.studentID+"'  hidden/></form><button  id='"+obj.ID+"' class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterAbsent(this)' name='"+obj.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
              result += "</tr>";
              $value.append(result);
            }
          }
        }
    )
  }
  $('#id').val('');
});

$('#namecommit').click(function () {
  if ($('#name').val() == ''){
    sendWarningToTop("姓名不能为空",2000,function () {
      console.log("姓名空")
    })
  }else {
    $("#namecommit").attr({"disabled":'disabled'});
    setTimeout(function(){ $("#namecommit").removeAttr("disabled"); }, 1500);
    let name = $('#name').val();
    let $value = $('#value');
    let $absent = $('#absent');
    $.get('/name/'+name,function (data) {
      console.log(data)
      if (data == 'false'){
        sendWarningToTop("未找到该名称学生",5000,function () {
          console.log("未找到该学号学生")
        })
      }else if (data.studentID != undefined){

        /**
         * 当返回结果为一个人且有逃课记录时
         */
        if (data.absentType != undefined){
          $('.title').remove()
          $value.empty();
          let title = "";
          title += "<th class='title'>"+"逃课日期"+"</th>";
          title += "<th class='title'>"+"第几节课"+"</th>";
          title += "<th class='title'>"+"课程名称"+"</th>";
          title += "<th class='title'>"+"逃课类型"+"</th>";
          title += "<th class='title operation'>"+"操作"+"</th>";
          $absent.append(title);
          let result = "";
          result += "<tr class='stu"+data.studentID+"'>";
          result += "<td>" + data.studentID + "</td>";
          result += "<td>" + data.name + "</td>";
          result += "<td>" + data.sex + "</td>";
          result += "<td>" + data.age + "</td>";
          result += "<td>" + data.classNum + "</td>";
          result += "<td>" + data.period + "</td>";
          result += "<td>" + data.checkData + "</td>";
          result += "<td>" + data.courseName + "</td>";
          result += "<td>" + data.absentType + "</td>";
          result += "<td><a onclick='deleteAbsent(this)' name='"+data.studentID+"'class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
              "<form method='post' class='hideForm"+data.studentID+"' hidden><input name='beforeID'  class='input"+data.studentID+"'  hidden/></form><button  id='"+data.ID+"' class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterAbsent(this)' name='"+data.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
          result += "</tr>";
          $value.append(result);
        }else {
          $('.title').remove();
          $value.empty();
          let result = "";
          let title;
          title += "<th class='title operation'>"+"操作"+"</th>";
          $absent.append(title);
          result += "<tr class='stu"+data.studentID+"'>";
          result += "<td>" + data.studentID + "</td>";
          result += "<td>" + data.name + "</td>";
          result += "<td>" + data.sex + "</td>";
          result += "<td>" + data.age + "</td>";
          result += "<td>" + data.classNum + "</td>";
          result += "<td><a onclick='deleteID(this)' name='"+data.studentID+"' class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
              "<form method='post' class='hideForm"+data.studentID+"' hidden><input name='beforeID'  class='input"+data.studentID+"'  hidden/></form><button  class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterStudent(this)' name='"+data.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
          result += "</tr>";
          $value.append(result);
        }
      }else {
        $value.empty();
        //返回人数为多个人
        for (let obj of data){
          $('.title').remove();
          let title = "";
          title += "<th class='title'>"+"逃课日期"+"</th>";
          title += "<th class='title'>"+"第几节课"+"</th>";
          title += "<th class='title'>"+"课程名称"+"</th>";
          title += "<th class='title'>"+"逃课类型"+"</th>";
          title += "<th class='title operation'>"+"操作"+"</th>";
          $absent.append(title);
          /**
           * 当有多个人的时候且有缺课记录
           */
          if (obj.absentType != undefined){
            let result = "";
            result += "<tr class='stu"+obj.studentID+"'>";
            result += "<td>" + obj.studentID + "</td>";
            result += "<td>" + obj.name + "</td>";
            result += "<td>" + obj.sex + "</td>";
            result += "<td>" + obj.age + "</td>";
            result += "<td>" + obj.classNum + "</td>";
            result += "<td>" + obj.period + "</td>";
            result += "<td>" + obj.checkData + "</td>";
            result += "<td>" + obj.courseName + "</td>";
            result += "<td>" + obj.absentType + "</td>";
            result += "<td><a onclick='deleteAbsent(this)' name='"+obj.studentID+"' class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
                "<form method='post' class='hideForm"+obj.studentID+"' hidden><input name='beforeID'  class='input"+obj.studentID+"'  hidden/></form><button  id='"+obj.ID+"' class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterAbsent(this)' name='"+obj.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
            result += "</tr>";
            $value.append(result);
          }else {
            /**
             * 有多个人但没缺课记录
             * @type {string}
             */
            let result = "";
            result += "<tr class='stu"+obj.studentID+"'>";
            result += "<td>" + obj.studentID + "</td>";
            result += "<td>" + obj.name + "</td>";
            result += "<td>" + obj.sex + "</td>";
            result += "<td>" + obj.age + "</td>";
            result += "<td>" + obj.classNum + "</td>";
            result += "<td>无</td>";
            result += "<td>无</td>";
            result += "<td>无</td>";
            result += "<td>无</td>";
            result += "<td><a onclick='deleteID(this)' name='"+obj.studentID+"' class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
                "<form method='post' class='hideForm"+obj.studentID+"' hidden><input name='beforeID'  class='input"+obj.studentID+"'  hidden/></form><button  class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterStudent(this)' name='"+obj.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
            result += "</tr>";
            $value.append(result);
          }
        }
      }
    })
  }
  $('#name').val('');
});

$('#coursecommit').click(function () {
  if ($('#course').val() == ''){
    sendWarningToTop("课程名不能为空",2000,function () {
      console.log("课程空")
    })
  }else {
    $("#coursecommit").attr({"disabled":'disabled'});
    setTimeout(function(){ $("#coursecommit").removeAttr("disabled"); }, 1500);
    let name = $('#course').val();
    let $value = $('#value');
    let $absent = $('#absent');
    $.get('/selectBycourse/'+name,function (data) {
      console.log(data)
      if (data == 'false'){
        sendWarningToTop("未找到该课程",5000,function () {
          console.log("未找到该课程")
        })
      }else if (data.studentID != undefined){

        /**
         * 当返回结果为一个人且有逃课记录时
         */
        if (data.absentType != undefined){
          $('.title').remove()
          $value.empty();
          let title = "";
          title += "<th class='title'>"+"逃课日期"+"</th>";
          title += "<th class='title'>"+"第几节课"+"</th>";
          title += "<th class='title'>"+"课程名称"+"</th>";
          title += "<th class='title'>"+"逃课类型"+"</th>";
          title += "<th class='title operation'>"+"操作"+"</th>";
          $absent.append(title);
          let result = "";
          result += "<tr class='stu"+data.studentID+"'>";
          result += "<td>" + data.studentID + "</td>";
          result += "<td>" + data.name + "</td>";
          result += "<td>" + data.sex + "</td>";
          result += "<td>" + data.age + "</td>";
          result += "<td>" + data.classNum + "</td>";
          result += "<td>" + data.period + "</td>";
          result += "<td>" + data.checkData + "</td>";
          result += "<td>" + data.courseName + "</td>";
          result += "<td>" + data.absentType + "</td>";
          result += "<td><a onclick='deleteAbsent(this)' name='"+data.studentID+"'class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
              "<form method='post' class='hideForm"+data.studentID+"' hidden><input name='beforeID'  class='input"+data.studentID+"'  hidden/></form><button  id='"+data.ID+"' class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterAbsent(this)' name='"+data.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
          result += "</tr>";
          $value.append(result);
        }
      }else {
        $('.title').remove();
        let title = "";
        title += "<th class='title'>"+"逃课日期"+"</th>";
        title += "<th class='title'>"+"第几节课"+"</th>";
        title += "<th class='title'>"+"课程名称"+"</th>";
        title += "<th class='title'>"+"逃课类型"+"</th>";
        title += "<th class='title operation'>"+"操作"+"</th>";
        $value.empty();
        $absent.append(title);
        for (let obj of data){
          /**
           * 当有多个人的时候
           */
          if (obj.absentType != undefined){
            let result = "";
            result += "<tr class='stu"+obj.studentID+"'>";
            result += "<td>" + obj.studentID + "</td>";
            result += "<td>" + obj.name + "</td>";
            result += "<td>" + obj.sex + "</td>";
            result += "<td>" + obj.age + "</td>";
            result += "<td>" + obj.classNum + "</td>";
            result += "<td>" + obj.period + "</td>";
            result += "<td>" + obj.checkData + "</td>";
            result += "<td>" + obj.courseName + "</td>";
            result += "<td>" + obj.absentType + "</td>";
            result += "<td><a onclick='deleteAbsent(this)' name='"+obj.studentID+"' class=\"layui-btn layui-btn-primary layui-btn-sm layui-border-blue\" style='text-decoration: none'>删除 <i class='layui-icon layui-icon-delete'></i></a>" +
                "<form method='post' class='hideForm"+obj.studentID+"' hidden><input name='beforeID'  class='input"+obj.studentID+"'  hidden/></form><button  id='"+obj.ID+"' class='layui-btn layui-btn-primary layui-btn-sm layui-border-blue alter' onclick='alterAbsent(this)' name='"+obj.studentID+"'>修改 <i class='layui-icon layui-icon-edit'></i></button></td>";
            result += "</tr>";
            $value.append(result);
          }
        }
      }
    })
  }
  $('#course').val('');
});

deleteID = function(object)  {
  let parent = $(object).parent().parent();
  layer.confirm('真的删除该学生记录吗?', function(){
        let name = $(object).attr("name");
        $.get('/deleteStudent/'+name,function (data) {
          console.log(data);
          let s = ".stu"+name;
            if (data == 'true'){
              layer.msg('删除成功', {icon: 1});
              $(s).remove();
            }else {
              layer.msg('删除失败', {icon: 2});
            }
        })
  });
};

deleteAbsent = function (object) {
  let parent = $(object).parent().parent();
  layer.confirm('真的删除该缺课记录吗?',{btn: ['仅删除缺课记录','学生记录也删除']}
  ,function(){
    let name = $(object).attr("name");
    $.get('/deleteabsent/'+name,function (data) {
      console.log(data);
      let s = ".stu"+name;
      if (data == 'true'){
        layer.msg('删除成功', {icon: 1});
        $(s).remove();
      }else {
        layer.msg('删除失败', {icon: 2});
      }
    })
  }
  ,function () {
    let name = $(object).attr("name");
    $.get('/deleteAll/'+name,function (data) {
      console.log(data)
      let s = ".stu"+name;
      if (data == 'true'){
        layer.msg('删除成功', {icon: 1});
        $(s).remove();
      }else {
        layer.msg('删除失败', {icon: 2});
      }
    })
  });
};