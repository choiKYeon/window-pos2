package com.example.windowsPos2.setting.service

import com.example.windowsPos2.converter.DtoConverter.convertToDto
import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.member.repository.MemberRepository
import com.example.windowsPos2.setting.dto.*
import com.example.windowsPos2.setting.entity.BreakTime
import com.example.windowsPos2.setting.entity.ClosedDays
import com.example.windowsPos2.setting.entity.EstimatedArrivalTime
import com.example.windowsPos2.setting.entity.EstimatedCookingTime
import com.example.windowsPos2.setting.entity.OperatePause
import com.example.windowsPos2.setting.entity.OperateTime
import com.example.windowsPos2.setting.entity.RegularHoliday
import com.example.windowsPos2.setting.entity.Setting
import com.example.windowsPos2.setting.entity.TemporaryHoliday
import com.example.windowsPos2.setting.repository.RegularHolidayRepository
import com.example.windowsPos2.setting.repository.SettingRepository
import com.example.windowsPos2.setting.repository.TemporaryHolidayRepository
import com.example.windowsPos2.setting.settingEnum.OperateStatus
import com.example.windowsPos2.setting.settingEnum.RegularClosedDays
import jakarta.transaction.Transactional
import org.springframework.cglib.core.Local
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime

@Service
class SettingService (
    private val settingRepository: SettingRepository,
    private val memberRepository: MemberRepository,
    private val regularHolidayRepository: RegularHolidayRepository,
    private val temporaryHolidayRepository: TemporaryHolidayRepository
) {
    //    세팅 세부 클래스 기본 설정
    @Transactional
    fun newSettingLogin(setting: Setting) {
        val operatePause = OperatePause.Builder().setting(setting).build()
        val operateTime = OperateTime.Builder().setting(setting).build()
        val estimatedCookingTime = EstimatedCookingTime.Builder().setting(setting).build()
        val estimatedArrivalTime = EstimatedArrivalTime.Builder().setting(setting).build()
        val closedDays = ClosedDays.Builder().setting(setting).build()
        val breakTime = BreakTime.Builder().setting(setting).build()
        val operateStatus = setting.operateStatus
    }

    // 유저의 세팅환경 갖고오쇼
    fun getSettingByMember(member: Member): SettingDto {
        val setting = settingRepository.findByMember(member).orElse(null)
        return convertToDto(setting)
    }

    //    유저 세팅환경 업데이트 구문
    @Transactional
    fun updateSetting(username: String, settingDto: SettingDto) {
        val member = memberRepository.findByUsername(username)
        val setting = settingRepository.findByMember(member!!).orElse(null)

        updateBreakTime(setting, settingDto.breakTimeDto)
        updateClosedDays(setting, settingDto.closedDaysDto)
        updateEstimatedArrivalTime(setting, settingDto.estimatedArrivalTimeDto)
        updateEstimatedCookingTime(setting, settingDto.estimatedCookingTimeDto)
        updateOperateTime(setting, settingDto.operateTimeDto)
        updateOperatePause(setting, settingDto.operatePauseDto)

        Setting.Builder(setting)
            .operateStatus(settingDto.operateStatus?.let { OperateStatus.valueOf(settingDto.operateStatus.toString()) })
            .build()

        settingRepository.save(setting)
    }

    //    브레이크 타임 업데이트 구문
    private fun updateBreakTime(setting: Setting, breakTimeDto: BreakTimeDto?) {
        if (breakTimeDto != null) {
            // 기존 breakTime 객체를 가져오거나 새로 생성
            var breakTime = setting.breakTime ?: run {
                val newBreakTime = BreakTime.Builder().setting(setting).build()
                Setting.Builder(setting).breakTime(newBreakTime).build()
                newBreakTime
            }

            // 평일 브레이크타임은 그대로 유지
            if (breakTimeDto.weekdayBreakTime != null) {
                breakTime = if (breakTimeDto.weekdayBreakTime == true) {
                    BreakTime.Builder(breakTime)
                        .weekdayBreakTime(true)
                        .weekdayBreakTimeStart(breakTimeDto.weekdayBreakTimeStart)
                        .weekdayBreakTimeEnd(breakTimeDto.weekdayBreakTimeEnd)
                        .build()
                } else {
                    BreakTime.Builder(breakTime)
                        .weekdayBreakTime(false)
                        .build()
                }
            }

            // 토요일 브레이크타임 수정 (평일 값은 유지)
            if (breakTimeDto.saturdayBreakTime != null) {
                breakTime = if (breakTimeDto.saturdayBreakTime == true) {
                    BreakTime.Builder(breakTime)
                        .saturdayBreakTime(true)
                        .saturdayBreakTimeStart(breakTimeDto.saturdayBreakTimeStart)
                        .saturdayBreakTimeEnd(breakTimeDto.saturdayBreakTimeEnd)
                        .build()
                } else {
                    BreakTime.Builder(breakTime)
                        .saturdayBreakTime(false)
                        .build()
                }
            }

            // 일요일 브레이크타임은 그대로 유지
            if (breakTimeDto.sundayBreakTime != null) {
                breakTime = if (breakTimeDto.sundayBreakTime == true) {
                    BreakTime.Builder(breakTime)
                        .sundayBreakTime(true)
                        .sundayBreakTimeStart(breakTimeDto.sundayBreakTimeStart)
                        .sundayBreakTimeEnd(breakTimeDto.sundayBreakTimeEnd)
                        .build()
                } else {
                    BreakTime.Builder(breakTime)
                        .sundayBreakTime(false)
                        .build()
                }
            }

            // 최종적으로 새로운 breakTime 객체를 설정
            BreakTime.Builder(breakTime)
                .setting(setting)
                .build()
        }
    }

    //    예상 도착 시간 업데이트 구문
    private fun updateEstimatedArrivalTime(setting: Setting, estimatedArrivalTimeDto: EstimatedArrivalTimeDto?) {
        if (estimatedArrivalTimeDto != null) {
            var estimatedArrivalTime = setting.estimatedArrivalTime

            // 기존 EstimatedArrivalTime 객체에서 빌더 패턴을 사용해 새로운 값 설정
            estimatedArrivalTime = EstimatedArrivalTime.Builder()
                .estimatedArrivalTime(estimatedArrivalTimeDto.estimatedArrivalTime ?: estimatedArrivalTime.estimatedArrivalTime)
                .estimatedArrivalTimeControl(estimatedArrivalTimeDto.estimatedArrivalTimeControl ?: estimatedArrivalTime.estimatedArrivalTimeControl)
                .setting(setting)
                .build()

            // setting에 새로 생성된 estimatedArrivalTime을 다시 할당
            EstimatedArrivalTime.Builder(estimatedArrivalTime)
                .setting(setting)
                .build()
        }
    }

    //    예상 조리 시간 업데이트 구문
    private fun updateEstimatedCookingTime(setting: Setting, estimatedCookingTimeDto: EstimatedCookingTimeDto?) {
        if (estimatedCookingTimeDto != null) {
            var estimatedCookingTime = setting.estimatedCookingTime

//            기존 EstimatedCookingTime 객체에서 빌더 패턴을 사용해 새로운 값 설정
            estimatedCookingTime = EstimatedCookingTime.Builder()
                .estimatedCookingTime(estimatedCookingTimeDto.estimatedCookingTime ?: estimatedCookingTime.estimatedCookingTime)
                .estimatedCookingTimeControl(estimatedCookingTimeDto.estimatedCookingTimeControl ?: estimatedCookingTime.estimatedCookingTimeControl)
                .setting(setting)
                .build()

//            setting에 새로 생성된 estimatedCookingTime을 다시 할당
            EstimatedCookingTime.Builder(estimatedCookingTime)
                .setting(setting)
                .build()
        }
    }

//    영업 시간 업데이트 구문
    private fun updateOperateTime(setting: Setting, operateTimeDto: OperateTimeDto?) {
        val operateTime = setting.operateTime ?: run {
            val newOperateTime = OperateTime.Builder().setting(setting).build()
            Setting.Builder(setting).operateTime(newOperateTime).build()
            newOperateTime
        }
        if (operateTimeDto != null) {
//            평일 시간 업데이트
            if (operateTimeDto.weekdayAllDay == true) {
                OperateTime.Builder(operateTime)
                    .weekdayAllDay(true)
                    .weekdayStartTime(LocalTime.of(0, 0))
                    .weekdayEndTime(LocalTime.of(0, 0))
                    .build()
            } else {
                OperateTime.Builder(operateTime)
                    .weekdayAllDay(false)
                    .weekdayStartTime(operateTimeDto.weekdayStartTime)
                    .weekdayEndTime(operateTimeDto.weekdayEndTime)
                    .build()
            }

//            토요일 시간 업데이트
            if (operateTimeDto.saturdayAllDay == true) {
                OperateTime.Builder(operateTime)
                    .saturdayAllDay(true)
                    .saturdayStartTime(LocalTime.of(0, 0))
                    .saturdayEndTime(LocalTime.of(0, 0))
                    .build()
            } else {
                OperateTime.Builder(operateTime)
                    .saturdayAllDay(false)
                    .saturdayStartTime(operateTimeDto.saturdayStartTime)
                    .saturdayEndTime(operateTimeDto.saturdayEndTime)
                    .build()
            }

//            일요일 시간 업데이트
            if (operateTimeDto.sundayAllDay == true) {
                OperateTime.Builder(operateTime)
                    .sundayAllDay(true)
                    .sundayStartTime(LocalTime.of(0, 0))
                    .sundayEndTime(LocalTime.of(0, 0))
                    .build()
            } else {
                OperateTime.Builder(operateTime)
                    .sundayAllDay(false)
                    .sundayStartTime(operateTimeDto.sundayStartTime)
                    .sundayEndTime(operateTimeDto.sundayEndTime)
                    .build()
            }

            OperateTime.Builder(operateTime)
                .setting(setting)
                .build()
        }
    }
//    영업 임시 중지 업데이트 구문
    private fun updateOperatePause(setting: Setting, operatePauseDto: OperatePauseDto?) {
        val operatePause = setting.operatePause ?: run {
            val newOperatePause = OperatePause.Builder().setting(setting).build()
            Setting.Builder(setting).operatePause(newOperatePause).build()
            newOperatePause
        }
/*영업 시작 전 상태에서 영업 시작하고 싶으면 START를 보내면 됨.
* BREAKTIME 도중에 영업을 시작하고 싶으면 START를 보내면 됨.
* PAUSE 도중에 영업을 시작하고 싶으면 START를 보내면 됨*/
        if (operatePauseDto != null) {
            val now = LocalTime.now()
            var endTime: LocalTime? = null
            OperatePause.Builder(operatePause)
                .operatePauseStartTime(operatePauseDto.operatePauseStartTime?: operatePause.operatePauseStartTime)
                .build()

//            임시 중지 종료 시간이 들어왔을 때 값을 저장하는 구문
            operatePauseDto.operatePauseEndTime?.let { OperatePause.Builder(operatePause)
                .operatePauseEndTime(operatePauseDto.operatePauseEndTime)
                .build()
            }
//            임시 중지 종료 시간을 분으로 전환해서 현재 시간에 추가해서 값을 저장하는 구문 (프론트에서 전환해서 추가하면 사용 안할 수 있음)
            operatePauseDto.operatePauseDuration?.let { endTime = now.plus(Duration.ofMinutes(it.toLong())) }
            endTime?. let { OperatePause.Builder(operatePause)
                .operatePauseEndTime(endTime)
                .build()
            }
            OperatePause.Builder(operatePause)
                .setting(setting)
                .build()
        }
    }

//    휴무일 업데이트 구문
    private fun updateClosedDays(setting: Setting, closedDaysDto: ClosedDaysDto?) {
        if (closedDaysDto != null) {
            val closedDays = setting.closedDays ?: run {
                val newClosedDays = ClosedDays.Builder().setting(setting).build()
                Setting.Builder(setting).closedDays(newClosedDays).build()
                newClosedDays
            }

            closedDaysDto.temporaryHolidayDates?.let { ClosedDays.Builder(closedDays).regularHolidayList(updateRegularHolidays(closedDays, closedDaysDto.regularHolidayList)) }
            closedDaysDto.regularHolidayList?.let { ClosedDays.Builder(closedDays).temporaryHolidayList(updateTemporaryHolidays(closedDays, closedDaysDto.temporaryHolidayDates)) }
            ClosedDays.Builder(closedDays)
                .setting(setting)
                .build()
        }
    }

//    정기 휴무 업데이트 구문
    private fun updateRegularHolidays(closedDays: ClosedDays, regularHolidayDtoList: List<RegularHolidayDto>?): List<RegularHoliday> {
        val existingHolidays = closedDays.regularHolidayList
        for (holiday in existingHolidays) {
            regularHolidayRepository.delete(holiday)
        }

//    기존 정기휴무 정보 전부 삭제
        closedDays.regularHolidayList.clear()
        val regularHolidays = mutableListOf<RegularHoliday>()
        if (regularHolidayDtoList != null) {
            for (regularHolidayDto in regularHolidayDtoList) {
                val regularHoliday = RegularHoliday.Builder()
                    .regularClosedDays(RegularClosedDays.valueOf(regularHolidayDto.regularClosedDays.toString()))
                    .dayOfWeek(DayOfWeek.valueOf(regularHolidayDto.dayOfWeek.toString()))
                    .closedDays(closedDays)
                    .build()

                regularHolidays.add(regularHoliday)
            }
        }
        return regularHolidays
    }

//    임시 휴무 업데이트 구문
    private fun updateTemporaryHolidays(closedDays: ClosedDays, temporaryHolidayDtoList: List<TemporaryHolidayDto>?): List<TemporaryHoliday> {
        val existingHolidays = closedDays.temporaryHolidayList
        for (holiday in existingHolidays) {
            temporaryHolidayRepository.delete(holiday)
        }

//    기존 임시휴무 정보 전부 삭제
        closedDays.temporaryHolidayList.clear()
        val temporaryHolidays = mutableListOf<TemporaryHoliday>()
        if (temporaryHolidayDtoList != null) {
            for (temporaryHoliday in temporaryHolidayDtoList) {
                val updateHoliday = TemporaryHoliday.Builder()
                    .temporaryHolidayStartDate(temporaryHoliday.temporaryHolidayStartDate)
                    .temporaryHolidayEndDate(temporaryHoliday.temporaryHolidayEndDate)
                    .closedDays(closedDays)
                    .build()

                temporaryHolidays.add(updateHoliday)
            }
        }
        return temporaryHolidays
    }
}
