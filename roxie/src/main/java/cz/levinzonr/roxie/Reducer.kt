
package cz.levinzonr.roxie

typealias Reducer<S, C> = suspend (state: S, change: C) -> S

