# AccessibilityDemo
学习总结辅助功能的第一步
---
**问题解决：关于直接调用辅助功能Service的getRootInActiveWindow（）为空，延时2s后调用就不为空的问题 ？**

1.问题重现
---
在辅助功能学习demo中， 在开启辅助功能权限后，手动打开一个AccessibilityActionActivity,在这个Activity的onResume中，根据节点文案（<font color = "#ff0000">"点击按钮"</font>）进行节点查找并**点击**，同时，延时2s后，再根据这个控件的ID（<font color = "#ff0000"><strong>R.id.btn_click</strong></font>）去查找进行**点击**。结果发现按照文案查找时，没有找到这个节点，但是延时2s后，根据ID去查找控件时，就能找到这个节点并执行点击。
<br/>经过断点发现，<font color = "#ff0000">**问题实质表现为，OnResume中，直接调用AccessibilityService.getRootInActiveWindow()为空。而延时2s后，再调用AccessibilityService.getRootInActiveWindow()就不为空，并正常使用。**</font>**所以，这是为什么？**

![](http://o9m6aqy3r.bkt.clouddn.com/%E8%BE%85%E5%8A%A9%E5%8A%9F%E8%83%BD%E6%A8%A1%E6%8B%9F%E7%82%B9%E5%87%BBActivity.png)

2.问题研究
---
我们都知道，通过辅助功能，可以以根节点遍历整个布局，并根据ID或者文案进行控件查找。


3.结论
--

