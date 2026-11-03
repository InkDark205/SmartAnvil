# Báo cáo ghi đè Smart Anvil

## File đã sửa
- `src/main/java/inkdark/smartanvil/gui/SmartAnvilConfigScreen.java`: bỏ `renderBackground(...)` khỏi `render(...)`, dùng `context.fill(...)` để tránh gọi blur lần thứ hai trong một frame.
- `README.md`: ghi rõ bản sửa và giới hạn chức năng hiện tại.
- `BUILD_GUIDE_VI.md`: bổ sung hướng dẫn và nguyên nhân crash.

## File giữ nguyên
- `gradle.properties`: Minecraft 1.21.11, Yarn `1.21.11+build.6`, Loader `0.19.3`, Fabric API `0.141.4+1.21.11`, Loom `1.17.13`, Java 21.
- Các file config, optimizer, safety, entrypoint và metadata không có lỗi được xác nhận trong crash mới nhất nên không thay đổi.

## Giới hạn xác minh
Môi trường hiện tại không có lệnh Gradle và không thể chạy build Gradle offline. Vì vậy gói này là source project đã sửa; chưa tạo JAR mới và không được xem JAR cũ là bản đã vá. Chức năng tự động thao tác anvil vẫn chưa được triển khai/kiểm chứng.


## Bổ sung trong lần cập nhật này

- Thêm màn hình `EnchantmentPriorityScreen` mở từ cấu hình Smart Anvil.
- Bảng hiển thị tên enchantment, thứ hạng ưu tiên và nút di chuyển lên/xuống.
- Thanh tìm kiếm lọc theo ID hoặc tên đã định dạng, không phân biệt chữ hoa/chữ thường.
- Lọc chỉ thay đổi danh sách đang hiển thị; thứ tự gốc không bị sắp xếp lại.
- Thứ tự được lưu vào `enchantmentPriorityCsv` trong `config/smartanvil.json`.
- Danh sách được lấy từ enchantment registry hiện có, đồng thời giữ lại ID đã lưu để không làm mất mục cấu hình cũ.


## Master toggle

Added persistent `enabled` setting (default `false`) and a first-row ON/OFF control on the main config screen. The SMART/PRIORITY selector remains a separate mode selector. This does not claim to add the not-yet-implemented automated anvil workflow.
