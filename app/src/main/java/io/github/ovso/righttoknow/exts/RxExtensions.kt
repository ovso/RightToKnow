package io.github.ovso.righttoknow.exts

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(subscribe: Disposable) {
  add(subscribe)
}
