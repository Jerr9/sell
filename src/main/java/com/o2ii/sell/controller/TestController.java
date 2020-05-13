package com.o2ii.sell.controller;

import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.enums.BasicEnum;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.result.ResponseData;
import com.o2ii.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController extends BaseController {

    @Autowired
    OrderMasterService orderMasterService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private Object obj = new Object();

    @GetMapping("saveByUrl")
    public String saveByUrl() throws Exception {
        URL imgUrl = new URL("http://img1.imgtn.bdimg.com/it/u=4210335887,3404193546&fm=26&gp=0.jpg");
        HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
        conn.setRequestMethod("GET");
        InputStream is = conn.getInputStream();
        byte[] data = new byte[1024];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int i = 0;
        while((i = is.read(data)) != -1) {
            output.write(data, 0, i);
            System.out.println(i);
        }
        output.close();
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\workspace\\java\\storage\\files\\images\\onepiece.png");
        fileOutputStream.write(output.toByteArray());
        output.close();
        log.info("【保存网络图片】= {}");
        return "Hello";
    }

    @PostMapping("uploadByStream")
    public String uploadByStream(@RequestParam("file") MultipartFile file) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\workspace\\java\\storage\\files\\images\\uploadByStream.png");

        IOUtils.copy(file.getInputStream(), fileOutputStream);
        return "Success";
    }

    @PostMapping("uploadByBase64")
    public ResponseData uploadByBase64(@RequestBody Map<String, String> paramMap) throws Exception {
        String base64Str = paramMap.get("base64");
        String fileName = paramMap.get("fileName");
        log.info(base64Str);
        if ("".equals(base64Str) || "".equals(fileName)) {
            return new ResponseData(BasicEnum.FAIL);
        }
        String[] list = base64Str.split("base64,");
        if (list.length == 2) {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] b = base64Decoder.decodeBuffer(list[1]);
            for(int i = 0; i < b.length; i++) {
                System.out.println(b[i]);
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\workspace\\java\\storage\\files\\images\\" + fileName + ".png");
            fileOutputStream.write(b);
            fileOutputStream.close();
        }
        else {
            return new ResponseData(BasicEnum.FAIL);
        }
        return new ResponseData(BasicEnum.SUCCESS);
    }

    @RequestMapping(value = "testAvailable", method = RequestMethod.POST)
    public ResponseData testAvailable() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("E:\\workspace\\java\\storage\\files\\images\\base64.png");
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] data = new byte[1024];
        int i = 0;
        while((i = fileInputStream.read(data)) > -1) {
            byteArrayInputStream.write(data);
        }
        String base64 = base64Encoder.encode(byteArrayInputStream.toByteArray());
        log.info("=======================");
        log.info(base64);
        return new ResponseData(BasicEnum.SUCCESS);
    }

    @RequestMapping(value = "testFindPage", method = RequestMethod.GET)
    public String findPage(int page, int size, Integer status, String uid) throws Exception {
        String baseParam = getBaseParam();
        String baseHead = getBaseHead();
        log.info("page = {}, size ={}, status = {}, uid = {}, baseParam = {}, baseHead = {}", page, size, status, uid, baseParam, baseHead);
        return "success";
    }

    @RequestMapping(value = "testGlobalExceptionHandler")
    public ResponseData testGlobalExceptionHandler(@RequestParam("openId") String openId,
                                                   @RequestParam("orderId") String orderId
    ) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderMasterService.findOneOrder(openId, orderId);
        ResponseData<OrderDTO> result = new ResponseData(BasicEnum.SUCCESS, orderDTO);
        return result;
    }

    // 高并发问题, Redis: setNX， Redisson
    @RequestMapping(value = "testConcurrent")
    public String testConcurrent() {
        String flag = "失败";
        synchronized(obj) {
            Integer realStock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (realStock >= 1) {
                realStock--;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("【库存剩余】= {}", realStock);
                return "Success: " + realStock;
            }
        }
        return "End: " + flag;
    }
}
