#$baseUrl = "http://localhost:8081"
#
#Write-Host "Welcome to the Flight Booking System!" -ForegroundColor Cyan
#
#while ($true) {
#    $resource = Read-Host "Choose what you want to manage: FLIGHT, BOOKING, USER (or type 'exit' to quit)"
#    if ($resource -eq "exit") { break }
#
#    # Show friendly options based on resource
#    $actionPrompt = switch ($resource.ToUpper()) {
#        "FLIGHT"  { "Choose operation: 1-View flights, 2-Add flight, 3-Update flight, 4-Delete flight" }
#        "BOOKING" { "Choose operation: 1-View bookings, 2-Book a flight, 3-Cancel a booking" }
#        "USER"    { "Choose operation: 1-View users, 2-Register new user, 3-Delete user" }
#        Default   { Write-Host "Invalid choice! Enter FLIGHT, BOOKING, USER or exit." -ForegroundColor Red; continue }
#    }
#    $actionChoice = Read-Host $actionPrompt
#
#    # Map friendly numbers to operations
#    $operation = switch ($actionChoice) {
#        "1" { "GET" }
#        "2" { "POST" }
#        "3" { if ($resource -eq "FLIGHT") { "PUT" } else { "DELETE" } }
#        "4" { "DELETE" }
#        Default { Write-Host "Invalid operation choice!" -ForegroundColor Red; continue }
#    }
#
#    try {
#        switch ($resource.ToUpper()) {
#            "FLIGHT" {
#                switch ($operation) {
#                    "GET" {
#                        $id = Read-Host "Enter flight ID (leave blank for all)"
#                        if ([string]::IsNullOrWhiteSpace($id)) {
#                            $response = Invoke-RestMethod -Uri "$baseUrl/api/flights" -Method GET
#                        } else {
#                            $response = Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method GET
#                        }
#
#                        # Display in table
#                        if ($response -is [System.Array]) {
#                            $response | Format-Table flightNumber, airline, source, destination, departureTime, arrivalTime, availableSeats, price -AutoSize
#                        } else {
#                            $response | Format-Table flightNumber, airline, source, destination, departureTime, arrivalTime, availableSeats, price -AutoSize
#                        }
#                    }
#                    "POST" {
#                        $flightNumber = Read-Host "Enter flight number"
#                        $airline = Read-Host "Enter airline"
#                        $source = Read-Host "Enter source"
#                        $destination = Read-Host "Enter destination"
#                        $departureTime = Read-Host "Enter departure time"
#                        $arrivalTime = Read-Host "Enter arrival time"
#                        $availableSeats = Read-Host "Enter available seats"
#                        $price = Read-Host "Enter price"
#
#                        $body = @{
#                            flightNumber = $flightNumber
#                            airline = $airline
#                            source = $source
#                            destination = $destination
#                            departureTime = $departureTime
#                            arrivalTime = $arrivalTime
#                            availableSeats = [int]$availableSeats
#                            price = [double]$price
#                        } | ConvertTo-Json
#
#                        Invoke-RestMethod -Uri "$baseUrl/api/flights" -Method POST -ContentType "application/json" -Body $body
#                        Write-Host "✅ Flight added successfully." -ForegroundColor Green
#                    }
#                    "PUT" {
#                        $id = Read-Host "Enter flight ID to update"
#                        $flightNumber = Read-Host "Enter flight number"
#                        $airline = Read-Host "Enter airline"
#                        $source = Read-Host "Enter source"
#                        $destination = Read-Host "Enter destination"
#                        $departureTime = Read-Host "Enter departure time"
#                        $arrivalTime = Read-Host "Enter arrival time"
#                        $availableSeats = Read-Host "Enter available seats"
#                        $price = Read-Host "Enter price"
#
#                        $body = @{
#                            flightNumber = $flightNumber
#                            airline = $airline
#                            source = $source
#                            destination = $destination
#                            departureTime = $departureTime
#                            arrivalTime = $arrivalTime
#                            availableSeats = [int]$availableSeats
#                            price = [double]$price
#                        } | ConvertTo-Json
#
#                        Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method PUT -ContentType "application/json" -Body $body
#                        Write-Host "✅ Flight updated successfully." -ForegroundColor Green
#                    }
#                    "DELETE" {
#                        $id = Read-Host "Enter flight ID to delete"
#                        Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method DELETE
#                        Write-Host "✅ Flight deleted successfully." -ForegroundColor Green
#                    }
#                }
#            }
#
#            "BOOKING" {
#                switch ($operation) {
#                    "GET" {
#                        $id = Read-Host "Enter booking ID (leave blank for all)"
#                        if ([string]::IsNullOrWhiteSpace($id)) { $response = Invoke-RestMethod -Uri "$baseUrl/api/bookings" -Method GET }
#                        else { $response = Invoke-RestMethod -Uri "$baseUrl/api/bookings/$id" -Method GET }
#                        $response | ConvertTo-Json -Depth 10
#                    }
#                    "POST" {
#                        $userId = Read-Host "Enter user ID"
#                        $flightId = Read-Host "Enter flight ID"
#                        $seatsBooked = Read-Host "Enter number of seats to book"
#
#                        Invoke-RestMethod -Uri "$baseUrl/api/bookings/book?userId=$userId&flightId=$flightId&seatsBooked=$seatsBooked" -Method POST
#                        Write-Host "✅ Booking created successfully." -ForegroundColor Green
#                    }
#                    "DELETE" {
#                        $id = Read-Host "Enter booking ID to cancel"
#                        Invoke-RestMethod -Uri "$baseUrl/api/bookings/cancel/$id" -Method DELETE
#                        Write-Host "✅ Booking cancelled successfully." -ForegroundColor Green
#                    }
#                }
#            }
#
#            "USER" {
#                switch ($operation) {
#                    "GET" {
#                        $idOrEmail = Read-Host "Enter user ID or email (leave blank for all)"
#                        if ([string]::IsNullOrWhiteSpace($idOrEmail)) {
#                            $response = Invoke-RestMethod -Uri "$baseUrl/api/users" -Method GET
#                        }
#                        elseif ($idOrEmail -match "^[0-9]+$") {
#                            $response = Invoke-RestMethod -Uri "$baseUrl/api/users/$idOrEmail" -Method GET
#                        } else {
#                            $response = Invoke-RestMethod -Uri "$baseUrl/api/users/email/$idOrEmail" -Method GET
#                        }
#
#                        # Display in table
#                        if ($response -is [System.Array]) {
#                            $response | Format-Table id, name, email -AutoSize
#                        } else {
#                            $response | Format-Table id, name, email -AutoSize
#                        }
#                    }
#                    "POST" {
#                        $name = Read-Host "Enter name"
#                        $email = Read-Host "Enter email"
#                        $password = Read-Host "Enter password"
#
#                        $body = @{
#                            name = $name
#                            email = $email
#                            password = $password
#                        } | ConvertTo-Json
#
#                        Invoke-RestMethod -Uri "$baseUrl/api/users/register" -Method POST -ContentType "application/json" -Body $body
#                        Write-Host "✅ User registered successfully." -ForegroundColor Green
#                    }
#                    "DELETE" {
#                        $id = Read-Host "Enter user ID to delete"
#                        Invoke-RestMethod -Uri "$baseUrl/api/users/$id" -Method DELETE
#                        Write-Host "✅ User deleted successfully." -ForegroundColor Green
#                    }
#                }
#            }
#
#        }
#    } catch {
#        Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
#    }
#}


$baseUrl = "http://localhost:8081"

Write-Host "Welcome to the Flight Booking System!" -ForegroundColor Cyan

# Function: Admin menu
function AdminMenu {
    while ($true) {
        $resource = Read-Host "Choose what you want to manage: `nFLIGHT, BOOKING, USER (or type 'exit' to logout)"
        if ($resource -eq "exit") { break }

        $actionPrompt = switch ($resource.ToUpper()) {
            "FLIGHT"  { "Choose operation: `n1-View flights, 2-Add flight, 3-Update flight, 4-Delete flight" }
            "BOOKING" { "Choose operation: `n1-View bookings, 2-Book a flight, 3-Cancel a booking" }
            "USER"    { "Choose operation: `n1-View users, 2-Register new user, 3-Delete user" }
            Default   { Write-Host "Invalid choice!" -ForegroundColor Red; continue }
        }
        $actionChoice = Read-Host $actionPrompt
        $operation = switch ($actionChoice) {
            "1" { "GET" }
            "2" { "POST" }
            "3" { if ($resource -eq "FLIGHT") { "PUT" } else { "DELETE" } }
            "4" { "DELETE" }
            Default { Write-Host "Invalid operation choice!" -ForegroundColor Red; continue }
        }

        try {
            switch ($resource.ToUpper()) {
                "FLIGHT" {
                    switch ($operation) {
                        "GET" {
                            $id = Read-Host "Enter flight ID (leave blank for all)"
                            if ([string]::IsNullOrWhiteSpace($id)) {
                                $response = Invoke-RestMethod -Uri "$baseUrl/api/flights" -Method GET
                            } else {
                                $response = Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method GET
                            }
                            if ($response -is [System.Array]) {
                                $response | Format-Table flightNumber, airline, source, destination, departureTime, arrivalTime, availableSeats, price -AutoSize
                            } else {
                                $response | Format-Table flightNumber, airline, source, destination, departureTime, arrivalTime, availableSeats, price -AutoSize
                            }
                        }
                        "POST" {
                            $flightNumber = Read-Host "Enter flight number"
                            $airline = Read-Host "Enter airline"
                            $source = Read-Host "Enter source"
                            $destination = Read-Host "Enter destination"
                            $departureTime = Read-Host "Enter departure time"
                            $arrivalTime = Read-Host "Enter arrival time"
                            $availableSeats = Read-Host "Enter available seats"
                            $price = Read-Host "Enter price"

                            $body = @{
                                flightNumber = $flightNumber
                                airline = $airline
                                source = $source
                                destination = $destination
                                departureTime = $departureTime
                                arrivalTime = $arrivalTime
                                availableSeats = [int]$availableSeats
                                price = [double]$price
                            } | ConvertTo-Json

                            Invoke-RestMethod -Uri "$baseUrl/api/flights" -Method POST -ContentType "application/json" -Body $body
                            Write-Host "✅ Flight added successfully." -ForegroundColor Green
                        }
                        "PUT" {
                            $id = Read-Host "Enter flight ID to update"

                            try {
                                # Check if flight exists first
                                $existingFlight = Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method GET -ErrorAction Stop

                                # If found, then ask for new details
                                $flightNumber = Read-Host "Enter flight number"
                                $airline = Read-Host "Enter airline"
                                $source = Read-Host "Enter source"
                                $destination = Read-Host "Enter destination"
                                $departureTime = Read-Host "Enter departure time"
                                $arrivalTime = Read-Host "Enter arrival time"
                                $availableSeats = Read-Host "Enter available seats"
                                $price = Read-Host "Enter price"

                                $body = @{
                                    flightNumber   = $flightNumber
                                    airline        = $airline
                                    source         = $source
                                    destination    = $destination
                                    departureTime  = $departureTime
                                    arrivalTime    = $arrivalTime
                                    availableSeats = [int]$availableSeats
                                    price          = [double]$price
                                } | ConvertTo-Json

                                Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method PUT -ContentType "application/json" -Body $body
                                Write-Host "✅ Flight updated successfully." -ForegroundColor Green
                            }
                            catch {
                                Write-Host "❌ Flight with ID $id not found." -ForegroundColor Red
                            }
                        }

                        "DELETE" {
                            $id = Read-Host "Enter flight ID to delete"
                            Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method DELETE
                            Write-Host "✅ Flight deleted successfully." -ForegroundColor Green
                        }
                    }
                }
                "BOOKING" {
                    switch ($operation) {
                        "GET" {
                            $id = Read-Host "Enter booking ID (leave blank for all)"
                            if ([string]::IsNullOrWhiteSpace($id)) { $response = Invoke-RestMethod -Uri "$baseUrl/api/bookings" -Method GET }
                            else { $response = Invoke-RestMethod -Uri "$baseUrl/api/bookings/$id" -Method GET }
                            $response | ConvertTo-Json -Depth 10
                        }
                        "POST" {
                            $userId = Read-Host "Enter user ID"
                            $flightId = Read-Host "Enter flight ID"
                            $seatsBooked = Read-Host "Enter number of seats to book"
                            Invoke-RestMethod -Uri "$baseUrl/api/bookings/book?userId=$userId&flightId=$flightId&seatsBooked=$seatsBooked" -Method POST
                            Write-Host "✅ Booking created successfully." -ForegroundColor Green
                        }
                        "DELETE" {
                            $id = Read-Host "Enter booking ID to cancel"
                            try {
                                Invoke-RestMethod -Uri "$baseUrl/api/bookings/cancel/$id" -Method DELETE -ErrorAction Stop
                                Write-Host "✅ Booking cancelled successfully." -ForegroundColor Green
                            }
                            catch {
                                Write-Host "❌ Booking with ID $id not found." -ForegroundColor Red
                            }
                        }

                    }
                }
                "USER" {
                    switch ($operation) {
                        "GET" {
                            $idOrEmail = Read-Host "Enter user ID or email (leave blank for all)"
                            if ([string]::IsNullOrWhiteSpace($idOrEmail)) {
                                $response = Invoke-RestMethod -Uri "$baseUrl/api/users" -Method GET
                            } elseif ($idOrEmail -match "^[0-9]+$") {
                                $response = Invoke-RestMethod -Uri "$baseUrl/api/users/$idOrEmail" -Method GET
                            } else {
                                $response = Invoke-RestMethod -Uri "$baseUrl/api/users/email/$idOrEmail" -Method GET
                            }
                            if ($response -is [System.Array]) {
                                $response | Format-Table id, name, email -AutoSize
                            } else {
                                $response | Format-Table id, name, email -AutoSize
                            }
                        }
                        "POST" {
                            $name = Read-Host "Enter name"
                            $email = Read-Host "Enter email"
                            $password = Read-Host "Enter password"

                            # Check if user exists
                            try {
                                $existingUser = Invoke-RestMethod -Uri "$baseUrl/api/users/email/$email" -Method GET
                                # If GET succeeds, user exists
                                Write-Host "❌ User already exists! Please login." -ForegroundColor Red
                                continue
                            } catch {
                                # If 404, user does not exist → proceed to register
                            }

                            $body = @{
                                name = $name
                                email = $email
                                password = $password
                            } | ConvertTo-Json

                            try {
                                $response = Invoke-RestMethod -Uri "$baseUrl/api/users/register" -Method POST -ContentType "application/json" -Body $body
                                Write-Host "✅ User registered successfully." -ForegroundColor Green
                            } catch {
                                Write-Host "❌ Could not register user: $($_.Exception.Message)" -ForegroundColor Red
                            }
                        }
                        "DELETE" {
                            $id = Read-Host "Enter user ID to delete"
                            Invoke-RestMethod -Uri "$baseUrl/api/users/$id" -Method DELETE
                            Write-Host "✅ User deleted successfully." -ForegroundColor Green
                        }
                    }
                }
            }
        } catch {
            Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
        }
    }
}

# Function: User menu
function UserMenu($user)
{
    while ($true)
    {
        $action = Read-Host "Choose operation: 1-Search flights, 2-Book flight, 3-Cancel booking, 4-View my bookings, 5-View my profile, 6-Logout"
        if ($action -eq "6") { break }
        switch ($action)
        {
            "1" {
                $id = Read-Host "Enter flight ID (leave blank for all)"
                if ( [string]::IsNullOrWhiteSpace($id))
                {
                    $flights = Invoke-RestMethod -Uri "$baseUrl/api/flights" -Method GET
                }
                else
                {
                    $flights = Invoke-RestMethod -Uri "$baseUrl/api/flights/$id" -Method GET
                }
                if ($flights -is [System.Array])
                {
                    $flights | Format-Table flightNumber, airline, source, destination, departureTime, arrivalTime, availableSeats, price -AutoSize
                }
                else
                {
                    $flights | Format-Table flightNumber, airline, source, destination, departureTime, arrivalTime, availableSeats, price -AutoSize
                }
            }
            "2" {
                $flightId = [int](Read-Host "Enter flight ID to book")
                $seatsBooked = [int](Read-Host "Enter number of seats to book")

                # Check flight exists
                try
                {
                    $flight = Invoke-RestMethod -Uri "$baseUrl/api/flights/$flightId" -Method GET -ErrorAction Stop
                }
                catch
                {
                    Write-Host "❌ Flight with ID $flightId not found." -ForegroundColor Red
                    continue
                }

                # Booking
                try
                {
                    Invoke-RestMethod -Uri "$baseUrl/api/bookings/book?userId=$( $user.id )&flightId=$flightId&seatsBooked=$seatsBooked" -Method POST -ErrorAction Stop
                    Write-Host "✅ Booking created successfully." -ForegroundColor Green
                }
                catch
                {
                    Write-Host "❌ Booking failed: $( $_.Exception.Message )" -ForegroundColor Red
                }
            }

            "3" {
                $id = Read-Host "Enter booking ID to cancel"
                try
                {
                    Invoke-RestMethod -Uri "$baseUrl/api/bookings/cancel/$id" -Method DELETE -ErrorAction Stop
                    Write-Host "✅ Booking cancelled successfully." -ForegroundColor Green
                }
                catch
                {
                    Write-Host "❌ Booking with ID $id not found." -ForegroundColor Red
                }
            }

            "4" {
                try
                {
                    $userId = $user.id
                    $myBookings = Invoke-RestMethod -Uri "$baseUrl/api/bookings/user/$userId" -Method GET -ErrorAction Stop

                    if ($myBookings -is [System.Array] -and $myBookings.Count -gt 0)
                    {
                        $myBookings | Format-Table id, flightId, userId, seatsBooked, bookingDate, status -AutoSize
                    }
                    elseif ($myBookings -ne $null)
                    {
                        $myBookings | Format-Table id, flightId, userId, seatsBooked, bookingDate, status -AutoSize
                    }
                    else
                    {
                        Write-Host "❌ No bookings found." -ForegroundColor Yellow
                    }
                }
                catch
                {
                    # Handle 404 (no bookings) separately
                    if ($_.Exception.Response.StatusCode -eq 404)
                    {
                        Write-Host "❌ No bookings found." -ForegroundColor Yellow
                    }
                    else
                    {
                        Write-Host "❌ Could not fetch bookings: $( $_.Exception.Message )" -ForegroundColor Red
                    }
                }
            }


            "5" {
                $user | Format-Table id, name, email
            }

#            "6" {
#                Write-Host " Logged out successfully." -ForegroundColor Cyan
##                return  # exits the UserMenu function and goes back to main loop
#            }
            default {
                Write-Host "Invalid option" -ForegroundColor Red
            }
        }
    }
}

# ------------------------
# Main loop
while ($true) {
    $roleChoice = Read-Host "Are you Admin or User? `nEnter 1 for Admin `nEnter 2 for User `n(or type 'exit' to quit)"
    if ($roleChoice -eq "exit") { break }

    switch ($roleChoice) {
        "1" {
            # Admin login
            $email = Read-Host "Enter admin email"
            $password = Read-Host "Enter admin password"
            try {
                $admin = Invoke-RestMethod -Uri "$baseUrl/api/users/email/$email" -Method GET
                if ($admin.role -eq "ADMIN" -and $admin.password -eq $password) {
                    Write-Host "✅ Admin login successful." -ForegroundColor Green
                    AdminMenu
                } else {
                    Write-Host "❌ Invalid admin credentials." -ForegroundColor Red
                }
            } catch {
                Write-Host "❌ Admin not found." -ForegroundColor Red
            }
        }

        "2" {
            $exists = Read-Host "Do you already have an account? (yes/no)"

            if ($exists.ToLower() -in @("yes","y")) {
                # Login flow
                $email = Read-Host "Enter email"
                $password = Read-Host "Enter password"

                $body = @{
                    email = $email
                    password = $password
                } | ConvertTo-Json

                try {
                    $user = Invoke-RestMethod -Uri "$baseUrl/api/users/login" -Method POST -ContentType "application/json" -Body $body
                    Write-Host "✅ User login successful." -ForegroundColor Green
                    UserMenu $user   # <-- pass full user object
                } catch {
                    if ($_.Exception.Response.StatusCode -eq 401) {
                        Write-Host "❌ Invalid password." -ForegroundColor Red
                    } elseif ($_.Exception.Response.StatusCode -eq 404) {
                        Write-Host "❌ User not found." -ForegroundColor Red
                    } else {
                        Write-Host "❌ Login error: $($_.Exception.Message)" -ForegroundColor Red
                    }
                }

            } else {
                # Registration flow
                $name = Read-Host "Enter name"
                $email = Read-Host "Enter email"
                $password = Read-Host "Enter password"

                # Check if user exists
                try {
                    $existing = Invoke-RestMethod -Uri "$baseUrl/api/users/email/$email" -Method GET
                    if ($existing -ne $null) {
                        Write-Host "❌ User already exists! Please login." -ForegroundColor Red
                        continue
                    }
                } catch {
                    # 404 means user does not exist, safe to proceed
                }

                # Register user
                $body = @{
                    name = $name
                    email = $email
                    password = $password
                } | ConvertTo-Json

                try {
                    Invoke-RestMethod -Uri "$baseUrl/api/users/register" -Method POST -ContentType "application/json" -Body $body
                    Write-Host "✅ User registered successfully." -ForegroundColor Green

                    # Auto-login
                    $loginBody = @{
                        email = $email
                        password = $password
                    } | ConvertTo-Json

                    $user = Invoke-RestMethod -Uri "$baseUrl/api/users/login" -Method POST -ContentType "application/json" -Body $loginBody
                    UserMenu $user   # <-- pass full user object

                } catch {
                    Write-Host "❌ Registration error: $($_.Exception.Message)" -ForegroundColor Red
                }
            }
        }

        default { Write-Host "Invalid choice" -ForegroundColor Red }
    }
}


