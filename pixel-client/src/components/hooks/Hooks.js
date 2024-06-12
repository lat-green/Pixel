import {useEffect, useRef, useState} from "react";

export function useEventListener(
    eventType,
    callback,
    element = window
) {
    const callbackRef = useRef(callback)
    useEffect(() => {
        callbackRef.current = callback
    }, [callback])
    useEffect(() => {
        if (element == null) return
        const handler = (e) => callbackRef.current(e)
        element.addEventListener(eventType, handler)
        return () => element.removeEventListener(eventType, handler)
    }, [eventType, element])
}

export function useWindowSize() {
    const [windowSize, setWindowSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight,
    })
    useEventListener("resize", () => {
        setWindowSize({width: window.innerWidth, height: window.innerHeight})
    })
    return windowSize
}