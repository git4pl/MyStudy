>   adb shell am force-stop com.wb.study
>   sleep 1
>   adb shell am start-activity -W -n com.wb.study/.MainActivity | grep "TotalTime" | cut -d ' ' -f 2
> done
