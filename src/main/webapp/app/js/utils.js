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
			                color: '#999'
			            }
			        }
			    },
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    legend: {
			        data:['蒸发量','降水量','平均温度','平均湿度']
			    },
			    xAxis: [
			        {
			            type: 'category',
			            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			            axisPointer: {
			                type: 'shadow'
			            }
			        },
			        {
			            type: 'category',
			            data: ['2001','2002','2003','2004','2005','2006','2007','2008','2009','2010','2011','2012'],
			            axisPointer: {
			                type: 'shadow'
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            name: '水量',
			            position:'left',
			            offset: 0.5,
			            min: 0,
			            max: 250,
			            interval: 50,
			            axisLabel: {
			                formatter: '{value} ml'
			            }
			        },
			        {
			            type: 'value',
			            name: '温度',
			            min: 0,
			            max: 25,
			            interval: 5,
			            axisLabel: {
			                formatter: '{value} °C'
			            }
			        },
			        {
			            type: 'value',
			            name: '湿度',
			            min: 0,
			            max: 100,
			            interval: 25,
			            offset:-80,
			            splitLine:{
			                show:false // 不显示分割线
			            },
			            axisLine:{lineStyle:{type:'dotted',color:'red'}}, // 坐标线样式
			            position:'right',
			            axisLabel: {
			                formatter: '{value} %rh'
			            }
			        }
			    ],
			    series: [
			        {
			            name:'蒸发量',
			            type:'bar',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
			            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
			        },
			        {
			            name:'降水量',
			            type:'bar',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
			        },
			        {
			            name:'平均温度',
			            type:'line',
			            yAxisIndex: 1,
			            label: {
			                normal: {
			                    show: true,
			                    position: 'top'
			                }
			            },
			            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			        },
			        {
			            name:'平均湿度',
			            type:'line',
			            yAxisIndex: 2,
			            label: {
			                normal: {
			                    show: true,
			                    position: 'top'
			                }
			            },
			            data:[12.0, 12.2, 13.3, 41.5, 61.3, 80.2, 20.3, 23.4, 23.0, 56.5, 32.0, 61.2]
			        },
			        {
			            name:'平均风力',
			            type:'line',
			            xAxisIndex:1,
			            label: {
			                normal: {
			                    show: true,
			                    position: 'top'
			                }
			            },
			            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
			        },
			    ]
			};
			return option;
		}
	}
	
	module.exports = Utils;
});