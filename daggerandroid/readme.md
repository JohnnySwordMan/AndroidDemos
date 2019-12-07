### TODO  

1. Activity、Fragment简化  
2. 详细记录每个步骤

### Activity注入步骤  

1. 在应用级别的组件`Component`中，设置`AndroidInjectionModule`，以确保所有`dagger.android`框架中的类都可用  
2. 定义`SubComponent`，例如`MainActivitySubComponent`  
3. 将上述`SubComponent`添加到`Component`层级中。具体操作：定义一个绑定`SubComponent.Builder`的module，例如`BindMainActivityModule`，将
这个module添加到注入Application的Component中。
4. `Application`实现`HasActivityInjector`，并`@Inject`一个`DispatchingAndroidInjector`，在`activityInjector()`中返回。
5. 在`MainActivity`中`inject`，是在`onCreate`方法中注入，写在`super.onCreate`之前


### Fragment注入步骤  

基本同`Activity`，详见代码，需要注意是在`onAttach()`中注入


### Activity简化  

使用`@ContributesAndroidInjector`来代替之前步骤2和步骤3定义的一些`SubComponent`和`BindModule`

以`MainActivity`为例：

```java
// 步骤2：定义SubComponent
@Subcomponent(
    modules = [MainActivityModule::class,
        BindMainFragmentModule::class]
)
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
	 abstract class Builder : AndroidInjector.Builder<MainActivity>() {}
}


// 步骤3：将SubComponent添加到Component层级中
@Module(subcomponents = [MainActivitySubComponent::class])
abstract class BindMainActivityModule {

    // *，不能写成<out Activity>
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjector(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<*>
}
```  

现在通过`@ContributesAndroidInjector`改写上述代码，如下：  

```java
@Module
abstract class BindMainActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, BindMainFragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
```    

### 定义全局性的对象  

1. 需要用到`@Component.Builder`和`@BindsInstance`两个注解，代码如下：  

```java
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

2. 修改Application类  

```java
//        DaggerAppComponent.create().inject(this)
        DaggerAppComponent.builder().application(this).build().inject(this)
```   

3. 定义AppModule 

```java
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideLogin(): Login {
        return Login("呵呵呵呵", "123456")
    }
}
```




### 参考文献  

1. [Dagger & Android](https://dagger.dev/android)
2. [dagger.android使用解析](https://mundane799699.github.io/2018/06/10/AndroidInjector/)
3. [Dagger与安卓](https://gowa.club/Java/Dagger%E4%B8%8E%E5%AE%89%E5%8D%93.html)
4. [Dagger 2 在 Android 上的使用](https://yuweiguocn.github.io/dagger2-1/)
5. [Dagger 2 完全解析](https://johnnyshieh.me/posts/dagger-subcomponent/)
