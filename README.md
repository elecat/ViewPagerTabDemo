# ViewPagerTabDemo
It is a ViewPager+Tab Demo

这是个人写的小Demo，便于日后写ViewPager+Tab的时候做参考。还在完善中。

##遇到的问题总结：

1、控件在获取宽度的时候，返回了0值，这是因为获取控件宽度的代码先于控件绘制。
也就是说控件在还没有画出来，我们就去获取控件的大小，所以会返回0值。
解决办法：
关于控件获取高度，有三种方式。
如何得到控件的高度，这里写了三个方法。比较了这三个方法哪个好。感谢文章作者。
http://blog.csdn.net/johnny901114/article/details/7839512

2、在成功获取控件宽度后，可以计算出偏移量。监听viewPager的OnPageChange。当滑动的时候，改变下标位置。
但是发现偏移量offset为0。
这是因为，监听代码的执行先于得到offset的值。所以要把监听代码延迟执行。
关于延迟执行，可以有几种方法。1、启动线程，让线程sleep。2、使用定时器。3、使用hanlder的onPostDelay。
对比以上方法最后使用handler的onPostDelay.
这是因为现在要对viewPager进行监听。方法1和2都是在子线程中操作的。有可能造成线程不安全。
选方法3，hanlder的onPostDelay是在UI线程运行的。

3、cursor初始位置的确定，涉及如何确定控件位置。可参考一下文章
http://blog.csdn.net/tabactivity/article/details/9128271

4、在Fragment嵌套Fragment过程中，子Fragment布局显示为空白。
解决方法：adapter的构造方法里，要传过去getChildFragmentManager();

5、关于cursor偏移量的计算，以及cursor初始位置的确定。请看代码说明和图片“P60509-145013.jpg”

在写的Demo过程中，参考的文章：
http://lightman1024.lofter.com/post/1d2b14e6_7aa8fcd
http://blog.csdn.net/tabactivity/article/details/9128271（关于如何设置控件位置以及如何得到控件大小）

感谢文章的作者们。

