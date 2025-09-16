package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        // reqeustDto -> entity
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet())+1 : 1;
        memo.setId(maxId);

        // DB저장
        memoList.put(memo.getId(), memo);

        // entity -> responseDto
        MemoResponseDto responseDto = new MemoResponseDto(memo);

        return responseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        
        // Map to List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();
        
        return responseList;
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)){

            // 해당 메모 가져오기
            Memo memo = memoList.get(id);

            // 메모 수
            memo.update(requestDto);

            return memo.getId();

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {

        // 해당 메모가 DB에 존재하는지 확
        if(memoList.containsKey(id)){

            // 해당 메모 삭제하
            memoList.remove(id);
            return id;

        } else {
            throw new IllegalArgumentException("삭제할 메모는 존재하지 않습니다.");
        }
    }
}
