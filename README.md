# ViewPagerTabDemo
It is a ViewPager+Tab Demo

这是个人写的小Demo，便于日后写ViewPager+Tab的时候做参考。还在完善中。

##遇到的难点总结：
1、控件在获取宽度的时候，返回了0值，这是因为获取控件宽度的代码先于控件绘制。
也就是说控件在还没有画出来，我们就去获取控件的大小，所以会返回0值。
解决办法：
关于控件获取高度，有三种方式。
如何得到控件的高度，这里写了三个方法。比较了这三个方法哪个好。感谢一下文章的作者。
http://blog.csdn.net/johnny901114/article/details/7839512

2、在成功获取控件宽度后，可以计算出偏移量。监听viewPager的OnPageChange。当滑动的时候，改变下标位置。
但是发现偏移量offset为0。
这是因为，监听代码的执行先于得到offset的值。所以要把监听代码延迟执行。
关于延迟执行，可以有几种方法。1、启动线程，让线程sleep。2、使用定时器。3、使用hanlder的onPostDelay。
对比以上方法最后使用handler的onPostDelay.
这是因为现在要对viewPager进行监听。方法1和2都是在子线程中操作的。有可能造成线程不安全。
选方法3，hanlder的onPostDelay是在UI线程运行的。


