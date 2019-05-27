# coding=utf-8
# 二项式分布——画图
# 导入需要的包
import numpy as np
import scipy.stats as stats
import matplotlib.pyplot as plt
import matplotlib.style as style
from IPython.core.display import HTML

# PLOTTING CONFIG 绘图配置
# style.use('fivethirtyeight')
plt.rcParams['figure.figsize'] = (14, 7)
plt.figure(dpi=200)

# PMF  绘制概率质量函数
plt.bar(left=(np.arange(10)), height=(stats.binom.pmf(np.arange(10), p=0.15, n=10)), width=0.75,
         edgecolor='black', color='black', alpha=0.9)
# n=20,P=0.5,绘制成柱形图

# CDF
# plt.plot(np.arange(20), stats.binom.cdf(np.arange(20), p=0.5, n=20), color="#fc4f30")  # 绘制该二项式的累积密度函数曲线

# LEGEND 图例
# plt.text(x=7.5, y=0.2, s="pmf(binomed)", alpha=0.75, weight="bold", color="#008fd5")
# plt.text(x=14.5, y=0.9, s="cdf", rotation=.75, weight="bold", color="#fc4f30")
plt.axis('off')
# plt.show()
plt.savefig('prob_hist.png')
