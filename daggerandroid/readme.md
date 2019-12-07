## Dagger.Android使用说明  

1. 为`Activity`注入对象
2. 为`Fragment`注入对象
3. 简化`Activity`注入对象步骤
4. 简化`Fragment`注入对象步骤
5. 如何定义全局性的对象
6. `DaggerApplication`、`DaggerActivity`以及`DaggerFragment`的使用
7. `AndroidInjection.inject(this)`原理，以及该方法需要在`Activity的onCreate`调用，`Fragment的onAttach()`调用
8. `@Component.Builder`和`@BindsInstance`原理



## 为Activity注入对象   

一、在`Application Component`中安装`AndroidInjectionModule`，以确保所有的类是可用的

```java
@Component(
    modules = [AndroidInjectionModule::class,
    ...]
)
interface AppComponent : AndroidInjector<DaApplication> {
   fun inject(application: DaApplication)
}
```  

二、创建`SubComponent`

继承`AndroidInjector`，且提供一个`AndroidInjector.Builder`的子类

```java
@Subcomponent(modules = [...])
interface MainActivitySubComponent : AndroidInjector<MainActivity> {
	@Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>() {}
}
```  

三、将`SubComponent`添加到`Application Component`层级  

**做法：** 定义一个绑定`SubComponent.Builder`的`module`，然后将该`module`添加到`Application Component`中。使用`@Binds`将`MainActivitySubComponent.Builder`绑定到`AndroidInjector.Factory`中 

```java
@Module(subcomponents = [MainActivitySubComponent::class])
abstract class BindMainActivityModule {
    // *，不能写成<out Activity>
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjector(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<*>
}
```   

四、将`module`添加到`Application Component`中  

```java
@Component(
    modules = [AndroidInjectionModule::class,
    BindMainActivityModule::class,
    ...]
)
interface AppComponent : AndroidInjector<DaApplication> {
   fun inject(application: DaApplication)
}
```  

五、修改`Application`  

1. 继承`HasActivityInjector`
2. `@Inject DispatchingAndroidInjector<Activity>`
3. 实现`activityInjector()`

```java
class DaApplication : Application(), HasActivityInjector {


    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)
    }

}
```  

六、在`Activity的onCreate`方法中调用`AndroidInjection.inject(this)`，必须在`super.onCreate()`之前  

```java
class MainActivity: AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		AndroidInjection.inject(this)
		super.onCreate(savedInstanceState)
	}
}
```  

以上是完整的注入`activity`对象的流程。  

   

## 为Fragment注入对象     

流程基本同`Activity`

一、处理`Fragment`依附的`Activity`  

```java
class MainActivity : AppCompatActivity(), HasFragmentInjector {
	@Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }
}
```   

二、创建`SubComponent`  

```java
@Subcomponent(modules = [...])
interface MainFragmentSubComponent : AndroidInjector<MainFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainFragment>() {}
}
```  

三、创建`BindFragmentModule`   

```java
@Module(subcomponents = [MainFragmentSubComponent::class])
abstract class BindMainFragmentModule {

    // *，不能写成<out Fragment>
    @Binds
    @IntoMap
    @ClassKey(MainFragment::class)
    abstract fun bindMainFragmentInjector(builder: MainFragmentSubComponent.Builder): AndroidInjector.Factory<*>

}
```   

四、可以将`BindFragmentModule`安装在任何层级的`component`中，例如：fragment的、Activity的，甚至Application的，不过需要让对应的类实现`HasFragmentInjector`  

```java
// 这里安装在MainActivity的component中
@Subcomponent(
    modules = [MainActivityModule::class,
        BindMainFragmentModule::class]
)
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>() {}
}
```  

五、修改`Fragment`  

```java
class MainFragment : Fragment() {
	
	override fun onAttach(activity: Activity?) {
        AndroidInjection.inject(this)
        super.onAttach(activity)
    }
}
```
  

## 简化Activity和Fragment的注入   

上述步骤2、3，创建`SubComponent`和创建`BindModule`将`SubComponent`添加到`Component`层级上，是可以省略的，只需要使用`@ContributesAndroidInjector`替代。

```java
@Module
abstract class BindMainActivityModule {

    @ContributesAndroidInjector(modules = [...])
    abstract fun contributeMainActivity(): MainActivity
}

@Module
abstract class BindMainFragmentModule {

    @ContributesAndroidInjector(modules = [...])
    abstract fun contributeMainFragment(): MainFragment
}
```     

## 定义全局对象  

例如：定义`loginServce`，只想定义成全局单例。


一、使用`@Component.Builder`和`@BindsInstance`注解改写`AppComponent`

```java
@Component(
    modules = [AndroidInjectionModule::class,
        AppModule::class,
        BindMainActivityModule::class]
)
interface AppComponent {
    fun inject(application: DaApplication)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}
```   

二、改写`Application`代码

```java
// 新增application(this)方法，该方法就是AppComponent中使用@BindsInstance注解的方法
 override fun onCreate() {
        super.onCreate()
//        DaggerAppComponent.create().inject(this)
        DaggerAppComponent.builder().application(this).build().inject(this)
    }
```  

三、定义一个module，将其添加到AppComponent上

```java
@Module
class AppModule {

    @PerApplication  // 使用注解标记生命周期
    @Provides
    fun provideLogin(): Login {
        return Login("呵呵呵呵", "123456")
    }
}
```   

## DaggerApplication的使用  

一、`DaggerApplication`的修改  

```java
// 不在onCreate方法中返回DaggerAppComponent对象
class DaApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return  DaggerAppComponent.builder().application(this).build()
    }
}
```  

二、`DaggerAppCompatActivity`的修改  

什么都不需要添加，包括`AndroidInjection.inject(this)`，`DaggerAppCompatActivity`已经处理





### 参考文献  

1. [Dagger & Android](https://dagger.dev/android)
2. [dagger.android使用解析](https://mundane799699.github.io/2018/06/10/AndroidInjector/)
3. [Dagger与安卓](https://gowa.club/Java/Dagger%E4%B8%8E%E5%AE%89%E5%8D%93.html)
4. [Dagger 2 在 Android 上的使用](https://yuweiguocn.github.io/dagger2-1/)
5. [Dagger 2 完全解析](https://johnnyshieh.me/posts/dagger-subcomponent/)
6. [[译] 全新 Android 注入器](https://juejin.im/post/5a39f26df265da4324809685)



