### VideoFeedPage

### Player in List设计

- 播放器设计
- 列表设计
- 生命周期一致设计

#### 播放器设计

MediaPlayerManager类管理单例的MediaPlayer，向外提供获取MediaPlayer的管理方法和消息通知的接口，用EventBus分发MediaPlayer生命周期状态。

#### 列表设计

基于RecyclerView的单列表，使用TextureView为视频渲染提供Surface，对于焦点Item设置激活状态，当Item被激活时，提供该Item的Surface给播放器，并且接收播放器的消息分发。

##### 如何确定当前item为激活状态

初始化时为播放按钮设置tag为当前的position，当播放按钮被点击时判断此时的tag并记录，便可把播放器的状态记录成

- （currentPosition!=tagPosition）初次点击，获取焦点
- (currentPosition==tagPositon)
  - (isPlaying) 正在播放，点击暂停
  - (!isPlaying) 正在暂停，点击继续

##### 如何处理生命周期事件

当接收到播放器管理者的事件通知后，通过`#findViewHolderForAdapterPosition()`方法获取到对应holder进行处理，当holder被回收的情况下直接刷新布局。

#### 生命周期一致设计

MediaPlayerManager分发MediaPlayer的生命周期情况，通过EventBus返给控制页面做相应的处理。

