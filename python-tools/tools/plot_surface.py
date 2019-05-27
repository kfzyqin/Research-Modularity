	# -*- coding: utf-8 -*-
# author:           inspurer(月小水长)
# pc_type           lenovo
# create_date:      2019/1/23
# file_name:        3DTest
# github            https://github.com/inspurer
# qq_mail           2391527690@qq.com

# -*- coding: utf-8 -*-
"""
绘制3d图形
"""

import matplotlib.pyplot as plt
import numpy as np
from mpl_toolkits.mplot3d import Axes3D
# 定义figure
fig = plt.figure()
# 创建3d图形的两种方式
# 将figure变为3d
ax = Axes3D(fig)

#ax = fig.add_subplot(111, projection='3d')

# 定义x, y
x = np.arange(-10, 10, 0.2)
y = np.arange(-10, 10, 0.2)

# 生成网格数据
X, Y = np.meshgrid(x, y)

print 'X: ', X
print 'Y: ', Y

int_x = X.astype(int) * 0.1
int_y = Y.astype(int) * 0.1
# 计算每个点对的长度
# R = X + Y
# print R.shape
# 计算Z轴的高度

sin_x = np.sin(X) + int_x
sin_y = np.sin(Y) + int_y
# sin_x = np.sin(X)
# sin_y = np.sin(Y)
Z = 0.5 * (sin_x + sin_y)

# 绘制3D曲面


# rstride:行之间的跨度  cstride:列之间的跨度
# rcount:设置间隔个数，默认50个，ccount:列的间隔个数  不能与上面两个参数同时出现


# cmap是颜色映射表
# from matplotlib import cm
# ax.plot_surface(X, Y, Z, rstride = 1, cstride = 1, cmap = cm.coolwarm)
# cmap = "rainbow" 亦可
# 我的理解的 改变cmap参数可以控制三维曲面的颜色组合, 一般我们见到的三维曲面就是 rainbow 的
# 你也可以修改 rainbow 为 coolwarm, 验证我的结论
ax.plot_surface(X, Y, Z, rstride = 1, cstride = 1, cmap = plt.get_cmap('coolwarm'))

# 绘制从3D曲面到底部的投影,zdir 可选 'z'|'x'|'y'| 分别表示投影到z,x,y平面
# zdir = 'z', offset = -2 表示投影到z = -2上
ax.contour(X, Y, Z, zdir = 'z', offset = -2, cmap = plt.get_cmap('coolwarm'))

# 设置z轴的维度，x,y类似
ax.set_zlim(-2, 2)
plt.savefig('asym_land.png', dpi=300)
plt.show()