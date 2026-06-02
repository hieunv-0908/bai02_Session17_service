Phần 1 - Phân tích logic

Vấn đề chính:

- Trong `JwtAuthenticationFilter` ban đầu, dòng gọi `tokenProvider.validateToken(jwt, null)` truyền `null` cho tham số `UserDetails`.
- Theo mẫu `JwtTokenProvider` từ bài học (Lesson 3), phương thức `validateToken(String token, UserDetails userDetails)` cần `UserDetails` để so sánh username (ví dụ `username.equals(userDetails.getUsername())`) hoặc để kiểm tra thông tin khác liên quan tới người dùng.
- Khi truyền `null`, `validateToken` sẽ không thể so sánh username và thường sẽ trả về `false` hoặc ném `NullPointerException`. Kết quả: filter không thiết lập Authentication và yêu cầu bảo vệ API bị chặn (403 Forbidden hoặc không nhận diện xác thực).

Nguyên nhân thực tế:

- Trật tự các bước xử lý trong filter không phù hợp. Thay vì:
  1) trích token
  2) gọi validateToken(token, null)
  3) nếu true -> lấy username -> load UserDetails

  Cần phải:
  1) trích token
  2) trích username từ token
  3) load `UserDetails` từ `UserDetailsService`
  4) gọi `validateToken(token, userDetails)`
  5) nếu hợp lệ -> tạo `Authentication` và đặt vào `SecurityContextHolder`

- Việc gọi `validateToken` trước khi có `UserDetails` là điểm không nhất quán giữa `JwtAuthenticationFilter` và `JwtTokenProvider`.

Phần 2 - Giải pháp:

- Chuyển việc lấy `UserDetails` lên trước khi gọi `validateToken` và truyền `UserDetails` đó vào `validateToken`.
- Nếu token hợp lệ so với `UserDetails`, tạo `UsernamePasswordAuthenticationToken` với `userDetails.getAuthorities()` và đặt vào `SecurityContextHolder`.

Ghi chú:

- Tài liệu bài học khuyến nghị `JwtAuthenticationFilter` chạy trước `UsernamePasswordAuthenticationFilter` và thiết lập `SecurityContext` nếu token hợp lệ. Việc sửa ở trên giúp đảm bảo filter có đầy đủ thông tin để chạy `validateToken` giống như mẫu trong bài học.

