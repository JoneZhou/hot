/**
 * 系统帮助类函数
 */
define(function(require, exports, module) {
	var templates = require('templates.js');
	
	/**
	 * 对外暴露的函数
	 */
	var Utils = {
		/**
		 * @param key 模块唯一标识，命名规则：包名+文件名（无扩展名） 如home.left
		 * @param json(optional) 用于渲染模版的json数据,改参数可为空，为空时直接返回模版内容
		 */
		template: function(key,json){
			var tpl = templates[key];
			if(json)	return _.template(tpl,json);
			return tpl;
		},
		chartOption: function(){
			var option = {
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'cross',
				            crossStyle: {
				                color: '#f00'
				            }
				        }
				    },
				    legend: {
				    	type:'scroll',
				        data:['经营','食物','娱乐','交通','衣服','食物','333','日常','理财','工资','住房']
				    },
				    xAxis: [
				        {
				            type: 'category',
				            data: ['2018-04月','2018-05月','2018-06月'],
				            axisPointer: {
				                type: 'shadow'
				            }
				        },
				         {
				            type: 'category',
				            data: ['收入','支出','收入','支出','收入','支出'],
				            axisTick:{show:true},
				            axisPointer: {
				                type: 'line'
				            },
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    dataZoom: [
				            
				            {
				                type: 'slider',
				                start: 0,
				                end: 100,
				                xAxisIndex:0
				            },
				            {
				                type: 'inside',
				                start: 0,
				                end: 100,
				                xAxisIndex:0
				            }
				        ],
				    series: [
				        {
				            name:'衣服',
				            type:'bar',
				            data:[null, null, null],
				            stack:'支出',
				            xAxisIndex:0,
				            label:{show:true,formatter: '{a}: {c}'}
				            
				        },
				        {
				        	name:'食物',
				        	type:'bar',
				        	data:[null, null, null],
				        	stack:'支出',
				        	xAxisIndex:0,
				        	label:{show:true,formatter: '{a}: {c}'}
				        
				        },
				        {
				        	name:'住房',
				        	type:'bar',
				        	data:[10, 36, 94],
				        	stack:'支出',
				        	xAxisIndex:0,
				        	label:{show:true,formatter: '{a}: {c}'}
				        
				        },
				        {
				            name:'工资',
				            type:'bar',
				            data:[20, 85, 65],
				            stack:'收入',
				            label:{show:true,formatter: '{a}: {c}'}
				        },
				        {
				            name:'经营',
				            type:'bar',
				            data:[20, 85, 65],
				            stack:'收入',
				            label:{show:true,formatter: '{a}: {c}'}
				        },
				        {
				            name:'理财',
				            type:'bar',
				            data:[20, 85, 65],
				            stack:'收入',
				            label:{show:true,formatter: '{a}: {c}'}
				        }
				    ]
				};
			return option;
		},
		/**
		 * 合并table列
		 */
		mergeTable: function  (tab,minCol,maxCol) {
            var  val, count, start;  //minCol maxCol：合并单元格作用从minCol到maxCol列
            for(var col = minCol; col < maxCol ; col++) {
                count = 1;
                val = "";
                for(var i=0; i<tab.rows.length; i++) {
                    var tdVal = "";
                    for(var j=col; j>=minCol; j--){
                    	tdVal += tab.rows[i].cells[j].innerHTML;
                    }
                	if(val == tdVal) {
                        count++;
                    } else {
                        if (count > 1) {
                            //合并
                            start = i - count;
                            tab.rows[start].cells[col].rowSpan = count;
                            for(var j=start+1; j<i; j++) {
                                tab.rows[j].cells[col].style.display = "none";
                            }
                            count = 1;
                        }
                        val = tdVal;
                    }
                }

                if(count > 1 ) {
                    //合并，最后几行相同的情况下
                    start = i - count;
                    tab.rows[start].cells[col].rowSpan = count;
                    for(var j=start+1; j<i; j++) {
                        tab.rows[j].cells[col].style.display = "none";
                    }
                }
            }
        }
	}
	
	module.exports = Utils;
});