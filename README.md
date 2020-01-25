# android-kotlin-countdown-timer
## Goal
カウントダウンタイマーを作る

## 必要な知識
- AppTheme
- ベクタ形式の画像
- FloatingActionButton
- CountDownTimer
- SoundPool
- 非推奨API処理

## CountDownTimerクラス
- [CountDownTimer - android developers](https://developer.android.com/reference/android/os/CountDownTimer)

### Summary

**Constructor**

```
public CountDownTimer (long millisInFuture, long countDownInterval)
```
- `millisInFuture`  long型:  `start()`がコールされてからカウントダウン終了で`onFinished()` が呼び出されるまでの時間をミリ秒で指定する。
- `countDownInterval`  long型:  `onTick(long)` でコールバックを受け取る時間間隔をt指定する。


**Methods**
 返り値 | メソッド | 説明
:---- | :---- | :----:
final void | `cancel()` | カウントダウンをかキャンセルする。
abstract void | `onFinish()` | カウントダウンが終了したらコールされる。
abstract void | `onTick(millisUntilFinished: Long)` | 一定時間が経過するとコールされる。
final CountDownTimer | `onStart()` | カウントダウンを開始する。

### 注意点



## 非推奨APIを処理する
- 実行中のOSバージョン番号は `Build.VERSION.SDK_INT` で取得できる。
- 今までリリースされたすべてのAndroid OSのバージョン番号は `Build.VERSION_CODES` で定義されている。

### Ex. AaPI21(Lollipop)以降で非推奨である対応例

SoundPoolのコンストラクタは, API21以降では非推奨となるため, SoundPool.Builderでインスタンスを生成して各種属性を設定する対応が必要となる。

```kt:
if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
    @Suppress("DEPRECATION")
    soundPool = SoundPool(2, AudioManager.STREAM_ALARM, 0)
} else {
    val audioAttributes
            = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ALARM)
        .build()
    soundPool = SoundPool.Builder()
        .setAudioAttributes(audioAttributes)
        .build()
}
```

※ `@Suppress("DEPRECATION")`というアノテーションは、「非推奨のメソッドを使っているが、対応済みなので検査不要だよ〜」ってことを明示的に示すもの。

### Build.VERSION_CODES の定数

定数名 | OSバージョン | バージョン番号
:---- | :---- | :----:
KITKAT | Android 4.4 | 19
LOLLIPOP | Android 5.0 | 21
LOLLIPOP_MR1 | Android 5.1 | 22
M | Android 6.0 | 23
N | Android 7.0 | 24
N_MR1 | Android 7.1 | 25
O | Android 8.0 | 26
O_MR1 | Android 8.1 | 27
P | Android 9.0 | 28
Q | Android 10.0 | 29
