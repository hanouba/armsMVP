1 存储所有单元格的 left right top bottom 坐标
    根据排列 有规律的 保存数据
    1-1    0+aveWidth*0,aveWidth,  0,gridHeight
    1-2    0+aveWidth*1,aveWidth*2,0,gridHeight

    2-1   0+aveWidth*0,aveWidth,  gridHeight*1,gridHeight*2

2 存储预案单元格 left right top bottom
3 滑动选择时,判断x 和 y 是否在相应的单元格中
    如果在就将对应的单元格 覆盖色块
4 抬起手后,提示用户是否确定
    确定: 保存数据
    取消:恢复之前的效果