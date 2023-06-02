
# TicketApi

# digram for datebease

![Image 01-06-2023 at 12 05 PM](https://github.com/ProgramerNawaf/TicketApi/assets/65816656/73aee4b2-a359-4036-ab2d-15e804febb26)


# Model 
- Ticket
- MyUser
- Company
- Event

# Relationship ( one to many )
- MyUser - Ticket 
- Event - Ticket
- Company - Event

# Endpoint
1.MyUser(Mohammed Abdulhadi)
- getALlMyUsers
- addMyUser
- updateMyUser
- deleteMyUser
- getByRole
- getUserById
- getUserTickets

2.Ticket
- buyTickets(Mohammed Alajaji)fixed the problem with how to calculate ticket prices and deduct it from customer balance to add it to customer revenue
- checkValidTicket(Mohammed Abdulhadi)fixed the problem with how to map Date to database and how to compare ticket date with current date
- cancelTicket(Nawaf Alahmed)fixed the problem with how to calculate ticket prices and deduct it from customer balance to add it to customer revenue


3.Company(Mohammed Alajaji)
- getCompanies
- addCompany
- updateCompany
- deleteCompany
- getCompanyEvents
- getCompaniesName
- getCompanyName

4-Event(Nawaf Alahmed)
- getEvents
- addEvent
- updateEvent
- deleteEvent
- ticketsLeft
- ticketsSold




 
