package com.msmith.base.observables;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;

public abstract class BaseCollection<T extends ReferenceCounted> extends BaseReferenceCounted
    implements Collection<T> {
}
