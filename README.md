# BinarFud-Backend

## [ SIB5 x Binar Academy ] - BackEnd Java
BinarFud-Backend adalah aplikasi pemesanan makanan secara online yang dibuat dengan menggunakan Framework Spring Boot <br /><br />

<br /><br />

## Desain Sistem Microservices

![Desain Sistem Microservices.jpg](https://github.com/Nanang-Wahyudi/BinarFud_v7/blob/challenge_7/src/main/resources/Desain%20Sistem%20Microservices.jpg?raw=true)
<br /><br />

## API Spec

### Login
<hr>

**Sign Up**<br />
Request :
- Method : POST
- Endpoint : `/api/auth/signup`
- Body :

```json
{
  "username": "string",
  "email": "string",
  "role": [
    "string"
  ],
  "password": "string"
}
```

**Sign In**<br />
Request :
- Method : POST
- Endpoint : `/api/auth/signin`
- Body :

```json
{
  "username": "string",
  "password": "string"
}
```
<br />


### User
<hr>

**Update User**<br />
  Request :
- Method : POST
- Path Variable : username of the user
- Endpoint : `/api/user/update/{username}`
- Body :

```json
{
    "userName": "string", 
    "email": "string",
    "password": "string"
}
```

**Delete User**<br />
Request :
- Method : DELETE
- Path Variable : username of the user
- Endpoint : `/api/user/delete/{username}`

**Get All User**<br />
Request :
- Method : GET
- Endpoint : `/api/user`

**Get User Detail**<br />
  Request :
- Method : GET
- Endpoint : `/api/user/detail`
- Params :

```
    Key -> username
    Value -> username of the user (example: Sabrina)
```
<br />


### Image Data
<hr>

**Upload Image**<br />
Request :
- Method : POST
- Endpoint : `/api/image/upload`
- Body :
  - form-data type file
```
    Key -> image
    Value -> Image File (example: Upload Image Nasi Goreng.jpg)
```

**Download Image**<br />
Request :
- Method : GET
- Path Variable : File Name of the Image
- Endpoint : `/api/image/download/{fileName}`
<br />


### Merchant
<hr>

**Create Merchant**<br />
Request :
- Method : POST
- Endpoint : `/api/merchant/add`
- Body :

```json
{
  "merchantCode": "string",
  "merchantName": "string",
  "merchantLocation": "string",
  "merchantStatus": "string"
}
```

**Update Merchant**<br />
Request :
- Method : PUT
- Path Variable : Old Merchant Code of the Merchant
- Endpoint : `/api/merchant/update/{oldMerchantCode}`
- Body :

```json
{
    "merchantCode": "INDKL", 
    "merchantName": "Indo Kuliner",
    "merchantLocation": "Jakarta",
    "merchantStatus": "OPEN"
}
```

**Delete Merchant**<br />
Request :
- Method : DELETE
- Path Variable : Merchant Code of the Merchant
- Endpoint : `/api/merchant/delete/{merchantCode}`

**Get All Merchant**<br />
Request :
- Method : GET
- Endpoint : `/api/merchant`

**Get Merchant Detail**<br />
Request :
- Method : GET
- Endpoint : `/api/merchant/detail`
- Params :

```
    Key -> merchantName
    Value -> Merchant Name of the Merchant (example: Indo Kuliner)
```
<br />


### Product
<hr>

**Add Product**<br />
Request :
- Method : POST
- Endpoint : `/api/product/add`
- Body :

```json
{
  "productCode": "string",
  "productName": "string",
  "productPrice": 0,
  "imageName": "string",
  "merchantCode": "string"
}
```

**Update Product**<br />
Request :
- Method : PUT
- Path Variable : Old Product Code of the Product
- Endpoint : `/api/product/update/{oldProductCode}`
- Body :

```json
{
  "productCode": "string",
  "productName": "string",
  "productPrice": 0,
  "imageName": "string",
  "merchantCode": "string"
}
```

**Delete Product**<br />
Request :
- Method : DELETE
- Path Variable : Product Code of the Product
- Endpoint : `/api/product/delete/{productCode}`

**Get All Product**<br />
Request :
- Method : GET
- Endpoint : `/api/product`

**Get Product Detail**<br />
Request :
- Method : GET
- Endpoint : `/api/product/detail`
- Params :

```
    Key -> productName
    Value -> Product Name of the Product (example: Nasi Goreng)
```

**Get Product By Merchant Name**<br />
Request :
- Method : GET
- Path Variable : Merchant Name of the Merchant
- Endpoint : `/api/product/{merchantName}`
<br />


### Order
<hr>

**Create Order**<br />
Request :
- Method : POST
- Endpoint : `/api/order/create`
- Body :

```json
{
  "username": "string",
  "productName": "string",
  "destinationAddress": "string",
  "quantity": 0
}
```

**Get All Order**<br />
Request :
- Method : GET
- Endpoint : `/api/order/orderDetail`
<br />


### Invoice
<hr>

**Generate Invoice to PDF**<br />
Request :
- Method : GET
- Endpoint : `/api/invoice/generate-invoice`
<br /><br />


## Tech Pemrograman Yang Dipakai  

- Spring Data JPA
- Spring Web
- Spring Security
- Spring Transactional
- Spring Scheduler
- API Documentation With Swagger
- Unit test with Spring Boot (Mock, Spy, Stub)
- Java Thread
- Java Logging
- Microservices 
- Firebase Cloud Messaging (FCM) 
- Jasper Report PDF (not finished)
- Message Broker (not finished)
<br /><br />


## ER Diagram 

![ERD binarFud_v4.png](https://github.com/Nanang-Wahyudi/BinarFud_v6/blob/challenge_6/src/main/resources/ERD%20binarFud_v6.png?raw=true)
<br /><br />


## Tools Yang Dipakai 

- Java 8
- Spring Boot 2.7.16
- Intellij IDEA
- PostgreSQL
